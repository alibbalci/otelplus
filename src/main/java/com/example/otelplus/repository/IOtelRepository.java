package com.example.otelplus.repository;

import com.example.otelplus.model.Otel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOtelRepository extends JpaRepository<Otel,Integer> {

}
