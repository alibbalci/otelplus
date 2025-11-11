package com.example.otelplus.repository;

import java.util.Optional; // Modelimizi import ediyoruz

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.otelplus.model.Kullanici;

@Repository // Bu sınıfın bir Repository olduğunu belirtir
public interface KullaniciRepository extends JpaRepository<Kullanici, Integer> {

    // Spring Data JPA, bu metot isimlerinden ne yapması gerektiğini anlar:
    // "SELECT * FROM kullanici WHERE kullanici_adi = ?"
    Optional<Kullanici> findByKullaniciAdi(String kullaniciAdi);

    // "SELECT * FROM kullanici WHERE kullanici_eposta = ?"
    Optional<Kullanici> findByKullaniciEposta(String kullaniciEposta);

    // "SELECT COUNT(*) > 0 FROM kullanici WHERE kullanici_adi = ?"
    Boolean existsByKullaniciAdi(String kullaniciAdi);

    // "SELECT COUNT(*) > 0 FROM kullanici WHERE kullanici_eposta = ?"
    Boolean existsByKullaniciEposta(String kullaniciEposta);
}