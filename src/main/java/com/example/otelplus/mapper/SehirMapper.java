package com.example.otelplus.mapper;

import com.example.otelplus.dto.SehirDto;
import com.example.otelplus.model.Sehir;

public class SehirMapper {

    public static SehirDto toDto(Sehir sehir) {
        return new SehirDto(
                sehir.getSehirId(),
                sehir.getAdi()
        );
    }
}
