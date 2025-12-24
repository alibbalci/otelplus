package com.example.otelplus.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class RezervasyonDto {
    private Integer rezervasyonId;
    private String otelAdi;
    private String odaTipi;
    private BigDecimal rezervasyonMaliyeti;
    private LocalDate girisTarihi;
    private LocalDate cikisTarihi;
}
