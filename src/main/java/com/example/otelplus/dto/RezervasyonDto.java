package com.example.otelplus.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RezervasyonDto {
    private Integer rezervasyonId;
    private String otelAdi;
    private String odaTipi;
    private Double rezervasyonMaliyeti;
    private LocalDate girisTarihi;
    private LocalDate cikisTarihi;
}
