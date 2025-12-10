package com.example.otelplus.repository;

import com.example.otelplus.model.Rezervasyon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface IRezervasyonRepository extends JpaRepository<Rezervasyon, Integer> {

    @Query("""
    SELECT r FROM Rezervasyon r
    WHERE r.oda.odaId = :odaId
      AND r.girisTarihi <= :cikis
      AND r.cikisTarihi >= :giris
    """)
    List<Rezervasyon> tarihCakisaniBul(
            @Param("odaId") Integer odaId,
            @Param("giris") LocalDate giris,
            @Param("cikis") LocalDate cikis
    );

    @Query("""
    SELECT r FROM Rezervasyon r
    WHERE r.oda.odaId = :odaId
      AND r.rezervasyonId <> :rezervasyonId
      AND r.girisTarihi <= :cikis
      AND r.cikisTarihi >= :giris
""")
    List<Rezervasyon> tarihCakisaniBulUpdate(
            @Param("rezervasyonId") Integer rezervasyonId,
            @Param("odaId") Integer odaId,
            @Param("giris") LocalDate giris,
            @Param("cikis") LocalDate cikis
    );


    List<Rezervasyon> findByKullaniciId(Integer kullaniciId);




}
