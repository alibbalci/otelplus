package com.example.otelplus.dto;

import java.util.List;

import lombok.Data;

@Data
public class SehirDetayDto {

    private Integer sehirId;
    private String sehirAdi;

    private List<GezilecekYerDto> gezilecekYerler;
    private List<RestoranDto> restoranlar;
    private List<EtkinlikDto> etkinlikler;
}
