package com.example.otelplus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.otelplus.model.Restoran;
import java.util.List;

public interface RestoranRepository extends JpaRepository<Restoran, Integer> {

    // Otel ID'sine göre restoranları bulur
    // (Restoran tablosundaki "otel_id" sütununa bakar)
    List<Restoran> findByOtelId(Integer otelId);
}