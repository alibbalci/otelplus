package com.example.otelplus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.otelplus.model.Sehir;

import java.util.Optional;

public interface SehirRepository extends JpaRepository<Sehir, Integer> {
    Optional<Sehir> findByAdiIgnoreCase(String adi);
}
