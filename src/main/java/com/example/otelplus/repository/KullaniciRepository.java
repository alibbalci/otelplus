package com.example.otelplus.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.otelplus.model.Kullanici;

@Repository
public interface KullaniciRepository extends JpaRepository<Kullanici, Integer> {

    Optional<Kullanici> findByKullaniciAdi(String kullaniciAdi);

    Optional<Kullanici> findByKullaniciEposta(String kullaniciEposta);

    Boolean existsByKullaniciAdi(String kullaniciAdi);

    Boolean existsByKullaniciEposta(String kullaniciEposta);

    // ✅ PostgreSQL procedure çağrısı
    @Transactional
    @Modifying
    @Query(value = """
        CALL sp_kullanici_olustur(
            :kullaniciAdi,
            :eposta,
            :sifre,
            :cinsiyet,
            :isim,
            :soyisim,
            :dogumTarihi,
            :ulke
        )
        """, nativeQuery = true)
    void spKullaniciOlustur(
            @Param("kullaniciAdi") String kullaniciAdi,
            @Param("eposta") String eposta,
            @Param("sifre") String sifre,
            @Param("cinsiyet") String cinsiyet,
            @Param("isim") String isim,
            @Param("soyisim") String soyisim,
            @Param("dogumTarihi") LocalDate dogumTarihi,
            @Param("ulke") String ulke
    );
}
