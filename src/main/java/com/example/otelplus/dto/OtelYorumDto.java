package com.example.otelplus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtelYorumDto {
    private Integer yorumId;
    private String kullaniciAdi;
    private String yorumIcerigi;
    private Double puan;
    private LocalDateTime yorumTarihi;
}