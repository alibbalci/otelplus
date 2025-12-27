package com.example.otelplus.dto;

import lombok.Data;


@Data
public class OtelDto {

    private Integer otelId;
    private String otelAdi;
    private String sehir;
    private String adres;
    private String telefon;
    private Double ortalamaPuan;
    private Double minFiyat;
}
