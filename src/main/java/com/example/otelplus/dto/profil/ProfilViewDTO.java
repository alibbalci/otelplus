package com.example.otelplus.dto.profil;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfilViewDTO {
    private String isim;
    private String soyisim;
    private LocalDate dogumTarihi;
    private String ulke;
    private String resim;
    private String kullaniciBiyografi;
    private String kullaniciAdi;
    private String kullaniciEposta;
}