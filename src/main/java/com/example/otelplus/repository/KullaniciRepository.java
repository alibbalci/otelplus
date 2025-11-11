package com.example.otelplus.repository;

import java.util.Optional; 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.otelplus.model.Kullanici;

@Repository 
public interface KullaniciRepository extends JpaRepository<Kullanici, Integer> {

    Optional<Kullanici> findByKullaniciAdi(String kullaniciAdi);

    Optional<Kullanici> findByKullaniciEposta(String kullaniciEposta);

    Boolean existsByKullaniciAdi(String kullaniciAdi);

    Boolean existsByKullaniciEposta(String kullaniciEposta);
}