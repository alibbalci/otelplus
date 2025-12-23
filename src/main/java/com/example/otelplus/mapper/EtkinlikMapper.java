package com.example.otelplus.mapper;

import com.example.otelplus.dto.EtkinlikDto;
import com.example.otelplus.model.Etkinlik;

public class EtkinlikMapper {

    public static EtkinlikDto toDto(Etkinlik etkinlik) {
        return new EtkinlikDto(
                etkinlik.getEtkinlikId(),
                etkinlik.getEtkinlikAdi(),
                etkinlik.getEtkinlikTuru(),
                etkinlik.getTarihi(),
                etkinlik.getIcerigi()
        );
    }
}
