package com.example.otelplus.dto;

public class MenuDto {

    private final Integer id;
    private final String urunAdi;
    private final String resimYolu;
    private final String ogunTuru;

    public MenuDto(Integer id, String urunAdi, String resimYolu, String ogunTuru) {
        this.id = id;
        this.urunAdi = urunAdi;
        this.resimYolu = resimYolu;
        this.ogunTuru = ogunTuru;
    }

    public Integer getId() {
        return id;
    }

    public String getUrunAdi() {
        return urunAdi;
    }

    public String getResimYolu() {
        return resimYolu;
    }

    public String getOgunTuru() {
        return ogunTuru;
    }
}
