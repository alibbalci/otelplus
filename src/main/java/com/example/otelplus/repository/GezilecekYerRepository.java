package com.example.otelplus.repository;

import com.example.otelplus.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GezilecekYerRepository extends JpaRepository<GezilecekYer, Integer> {
    List<GezilecekYer> findBySehir_SehirId(Integer sehirId);
}