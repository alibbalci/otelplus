package com.example.otelplus.dto;

public class RestoranDto {

    private final Integer id;
    private final String restoranAdi;
    private final String calismaSaatleri;

    public RestoranDto(Integer id, String restoranAdi, String calismaSaatleri) {
        this.id = id;
        this.restoranAdi = restoranAdi;
        this.calismaSaatleri = calismaSaatleri;
    }

    public Integer getId() {
        return id;
    }

    public String getRestoranAdi() {
        return restoranAdi;
    }

    public String getCalismaSaatleri() {
        return calismaSaatleri;
    }
}
