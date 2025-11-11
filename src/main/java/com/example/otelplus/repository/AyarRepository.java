package com.example.otelplus.repository;

import org.springframework.data.jpa.repository.JpaRepository; // Modelimizi import ediyoruz
import org.springframework.stereotype.Repository;

import com.example.otelplus.model.Ayarlar;

@Repository
public interface AyarRepository extends JpaRepository<Ayarlar, Integer> {
    // Şimdilik özel bir sorguya ihtiyacımız yok.
    // findById(), save(), findAll() gibi temel metotlar bize yeterli.
}