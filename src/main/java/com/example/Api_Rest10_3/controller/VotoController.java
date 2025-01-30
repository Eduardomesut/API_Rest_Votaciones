package com.example.Api_Rest10_3.controller;

import com.example.Api_Rest10_3.model.Voto;
import com.example.Api_Rest10_3.repository.VotoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/api")
public class VotoController {

    private final VotoRepository votoRepository;

    public VotoController(VotoRepository votoRepository) {
        this.votoRepository = votoRepository;
    }

    @GetMapping("/voto/{name}")
    public ResponseEntity<Voto> createVote(@PathVariable String name) {
        if (votoRepository.findByName(name) != null) {
            Voto update = votoRepository.findByName(name);
            update.setVotos(update.getVotos() + 1);
            votoRepository.save(update);
            return ResponseEntity.ok(update);

        } else {
            Voto nuevo = new Voto(name, 1);
            votoRepository.save(nuevo);
            return ResponseEntity.ok(nuevo);
        }
    }

    @GetMapping("/votos")
    public ResponseEntity<List<Voto>> getAllVotos() {

        List<Voto> votos = votoRepository.findAll();
        return ResponseEntity.ok(votos);
    }

    @GetMapping("/votos/orden")
    public ResponseEntity<List<Voto>> getAllVotosOrden() {

        List<Voto> votos = votoRepository.findAllByOrderByVotosDesc();
        return ResponseEntity.ok(votos);
    }

    @GetMapping("/votos/ganador")
    public ResponseEntity<Voto> getGanador() {

        List<Voto> votos = votoRepository.findAllByOrderByVotosDesc();
        if (votos.size() > 1) {
            Voto ganador = votos.get(0);
            return ResponseEntity.ok(ganador);
        }

        return ResponseEntity.badRequest().build();

    }

    //En el body del post no hay que poner id 
    @PostMapping("/votos")
    public ResponseEntity<Voto> insertarVoto(@RequestBody Voto voto) {
        if (voto.getId() != null || votoRepository.findByName(voto.getName()) != null) {
            return ResponseEntity.badRequest().build();
        }

        Voto nuevoVoto = votoRepository.save(voto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoVoto);
    }

    @GetMapping("/votos/ganador/style")
    public ResponseEntity<String> getGanadorStyle() {
        List<Voto> votos = votoRepository.findAllByOrderByVotosDesc();

        if (votos.isEmpty()) {
            return ResponseEntity.badRequest().body("<h2>No hay votos registrados</h2>");
        }

        Voto ganador = votos.get(0);

        String html = """
        <!DOCTYPE html>
        <html lang="es">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Ganador de la Votación</title>
            <style>
                body { font-family: Arial, sans-serif; text-align: center; margin: 50px; }
                .container { border: 2px solid #ccc; padding: 20px; border-radius: 10px; display: inline-block; }
                h1 { color: #007bff; }
                p { font-size: 20px; }
            </style>
        </head>
        <body>
            <div class="container">
                <h1>🏆 Ganador de la Votación</h1>
                <p><strong>Nombre:</strong> %s</p>
                <p><strong>Votos:</strong> %d</p>
            </div>
        </body>
        </html>
    """.formatted(ganador.getName(), ganador.getVotos());

        return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body(html);
    }

}
