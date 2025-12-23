package com.example.otelplus.dto;

import com.fasterxml.jackson.annotation.JsonFormat; // <--- BU IMPORT'U EKLE
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

    // ŞU SATIRI EKLE 👇
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime yorumTarihi;
}