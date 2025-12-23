package com.example.otelplus.dto;

public class SehirDto {

    private final Integer id;
    private final String adi;

    public SehirDto(Integer id, String adi) {
        this.id = id;
        this.adi = adi;
    }

    public Integer getId() {
        return id;
    }

    public String getAdi() {
        return adi;
    }
}
