package com.example.otelplus.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class GenelDetayliRaporDto {
    private String sehirAdi;
    private Long toplamOtel;
    private Long toplamOda;
    private Long toplamRezervasyon;
    private BigDecimal toplamCiro;
    private BigDecimal ortalamaRezTutar;
    private BigDecimal ortalamaKalisGece;
    private String enCokTercihEdilenOtel;
    private String enCokTercihEdilenOdaTipi;
    private Long sehirSiralama;
}
