package com.example.otelplus.dto;

public class GezilecekYerDto {

    private final Integer id;
    private final String yerAdi;
    private final String yerIcerigi;

    public GezilecekYerDto(Integer id, String yerAdi, String yerIcerigi) {
        this.id = id;
        this.yerAdi = yerAdi;
        this.yerIcerigi = yerIcerigi;
    }

    public Integer getId() {
        return id;
    }

    public String getYerAdi() {
        return yerAdi;
    }

    public String getYerIcerigi() {
        return yerIcerigi;
    }
}
