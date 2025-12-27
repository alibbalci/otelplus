package com.example.otelplus.dto;

import lombok.Data;



@Data
public class OtelYorumDtoIU {

    private Integer otelId;
    private Integer kullaniciId;   // ✅ eklen
    private String yorumIcerigi;
    private Double puan;
}

