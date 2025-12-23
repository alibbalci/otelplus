package com.example.otelplus.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.otelplus.model.Etkinlik;

public interface EtkinlikRepository extends JpaRepository<Etkinlik, Integer> {

    List<Etkinlik> findBySehir_SehirId(Integer sehirId);
}
