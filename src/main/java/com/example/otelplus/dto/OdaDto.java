package com.example.otelplus.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class OdaDto {
    private Integer odaId;
    private String odaTipi;
    private Integer kapasite;
    private BigDecimal fiyat;
}
