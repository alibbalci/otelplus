package com.example.otelplus.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequestDTO {

    @NotBlank(message = "Kullanıcı adı boş olamaz")
    @Size(min = 3, max = 50, message = "Kullanıcı adı 3 ile 50 karakter arasında olmalıdır")
    private String kullaniciAdi;

    @NotBlank(message = "E-posta boş olamaz")
    @Email(message = "Geçerli bir e-posta adresi giriniz")
    @Size(max = 100)
    private String kullaniciEposta;

    @NotBlank(message = "Şifre boş olamaz")
    @Size(min = 6, max = 255, message = "Şifre en az 6 karakter olmalıdır")
    private String kullaniciSifre;

    private String kullaniciCinsiyet;
}