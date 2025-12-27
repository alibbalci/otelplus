package com.example.otelplus.repository;

import com.example.otelplus.model.Rezervasyon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RaporRepository extends JpaRepository<Rezervasyon,Long> {
    @Query(value = "SELECT * FROM fn_tr_genel_detayli_rapor(:bas, :bit)", nativeQuery = true)
    List<Object[]> genelDetayli(@Param("bas") LocalDate bas,
                                @Param("bit") LocalDate bit);

    @Query(value = "SELECT * FROM fn_sehir_rezervasyon_ozet_raporu(:bas, :bit)", nativeQuery = true)
    List<Object[]> sehirOzet(@Param("bas") LocalDate bas,
                             @Param("bit") LocalDate bit);
}
