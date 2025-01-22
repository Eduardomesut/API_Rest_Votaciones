package com.example.Api_Rest10_3.repository;

import com.example.Api_Rest10_3.model.Voto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> {
    Voto findByName(String name);
    
    List<Voto> findAllByOrderByVotosDesc();
    

}
