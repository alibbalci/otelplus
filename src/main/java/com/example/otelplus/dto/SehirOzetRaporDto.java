package com.example.otelplus.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SehirOzetRaporDto {
    private String sehirAdi;
    private Long toplamRezervasyon;
    private BigDecimal toplamCiro;
    private BigDecimal ortalamaKalisGece;
    private String enCokOdaTipi;
    private BigDecimal enCokOdaOrtalamaFiyat;
}
