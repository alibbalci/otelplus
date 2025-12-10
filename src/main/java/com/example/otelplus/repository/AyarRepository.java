package com.example.otelplus.repository;

import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository;

import com.example.otelplus.model.Ayarlar;

@Repository
public interface AyarRepository extends JpaRepository<Ayarlar, Integer> {
    
   
}