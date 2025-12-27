package com.example.otelplus.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RegisterRequestDTO {

    @NotBlank(message = "Kullanıcı adı boş olamaz")
    @Size(min = 3, max = 50)
    private String kullaniciAdi;

    @NotBlank(message = "E-posta boş olamaz")
    @Email
    @Size(max = 100)
    private String kullaniciEposta;

    @NotBlank(message = "Şifre boş olamaz")
    @Size(min = 6, max = 255)
    private String kullaniciSifre;

    private String kullaniciCinsiyet;

    // ✅ Procedure için gerekli alanlar
    @NotBlank(message = "İsim boş olamaz")
    private String isim;

    @NotBlank(message = "Soyisim boş olamaz")
    private String soyisim;

    private LocalDate dogumTarihi;

    private String ulke;
}
