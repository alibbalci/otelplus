package com.example.otelplus.repository;

import com.example.otelplus.dto.OtelArama;
import com.example.otelplus.model.Otel;
import com.example.otelplus.dto.OtelArama;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IOtelRepository extends JpaRepository<Otel, Integer> {

    @Query(value = "SELECT * FROM search_otel_by_sehir_fn(:sehir, :sort)",
            nativeQuery = true)
    List<OtelArama> searchBySehirFn(
            @Param("sehir") String sehir,
            @Param("sort") String sort);
}
