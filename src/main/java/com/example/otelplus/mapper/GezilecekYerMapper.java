package com.example.otelplus.mapper;

import com.example.otelplus.dto.GezilecekYerDto;
import com.example.otelplus.model.GezilecekYer;

public class GezilecekYerMapper {

    public static GezilecekYerDto toDto(GezilecekYer yer) {
        return new GezilecekYerDto(
                yer.getYerId(),
                yer.getYerAdi(),
                yer.getYerIcerigi()
        );
    }
}
