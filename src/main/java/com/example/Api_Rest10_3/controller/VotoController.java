package com.example.Api_Rest10_3.controller;

import com.example.Api_Rest10_3.model.Voto;
import com.example.Api_Rest10_3.repository.VotoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class VotoController {

    private final VotoRepository votoRepository;


    public VotoController(VotoRepository votoRepository) {
        this.votoRepository = votoRepository;
    }

    @GetMapping ("/voto/{name}")
    public ResponseEntity<Voto> createVote(@PathVariable String name){
        if (votoRepository.findByName(name) != null){
            Voto update = votoRepository.findByName(name);
            update.setVotos(update.getVotos() + 1);
            votoRepository.save(update);
            return ResponseEntity.ok(update);

        }else {
            Voto nuevo = new Voto(name,1);
            votoRepository.save(nuevo);
            return ResponseEntity.ok(nuevo);
        }
    }

    @GetMapping("/votos")
    public ResponseEntity<List<Voto>> getAllVotos(){
        List<Voto> votos = votoRepository.findAll();
        return ResponseEntity.ok(votos);
    }
}
