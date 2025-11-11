package com.example.otelplus.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {

    @NotBlank(message = "Kullanıcı adı boş olamaz")
    private String kullaniciAdi;

    @NotBlank(message = "Şifre boş olamaz")
    private String kullaniciSifre;
}