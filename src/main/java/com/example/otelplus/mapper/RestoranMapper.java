package com.example.otelplus.mapper;

import com.example.otelplus.dto.RestoranDto;
import com.example.otelplus.model.Restoran;

public class RestoranMapper {

    public static RestoranDto toDto(Restoran restoran) {
        return new RestoranDto(
                restoran.getRestoranId(),
                restoran.getRestoranAdi(),
                restoran.getCalismaSaatleri()
        );
    }
}
