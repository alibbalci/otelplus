package com.example.otelplus.dto;

import lombok.Data;

import java.util.List;

@Data
public class OtelDetayDto {

    private Integer otelId;
    private String otelAdi;
    private String adres;
    private String sehir;
    private Double enlem;
    private Double boylam;

    private List<String> ozellikler;      // Sadece isim listesi
    private List<OdaDto> odalar;          // Oda DTO’ları
    private List<OtelYorumDto> yorumlar;  // Yorum DTO’ları
    private Double ortalamaPuan;          // Hesaplanmış değer
}