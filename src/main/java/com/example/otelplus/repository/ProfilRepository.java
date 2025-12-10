package com.example.otelplus.repository;

import java.util.Optional; 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.otelplus.model.ProfilBilgileri;

@Repository
public interface ProfilRepository extends JpaRepository<ProfilBilgileri, Integer> {


    Optional<ProfilBilgileri> findByKullanici_KullaniciId(Integer kullaniciId);
}