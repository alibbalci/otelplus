package com.example.otelplus.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class OdaDto {
    private Integer odaId;
    private String odaTipi;
    private Integer kapasite;
}
