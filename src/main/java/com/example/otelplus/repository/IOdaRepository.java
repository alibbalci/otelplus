package com.example.otelplus.repository;

import com.example.otelplus.model.Oda;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IOdaRepository extends JpaRepository<Oda, Integer> {
    List<Oda> findByOtel_OtelId(Integer otelId);
}

