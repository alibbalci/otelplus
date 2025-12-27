package com.example.otelplus.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthResponseDTO {

    private String token;
    private String kullaniciAdi;
    private Integer kullaniciId;
}
