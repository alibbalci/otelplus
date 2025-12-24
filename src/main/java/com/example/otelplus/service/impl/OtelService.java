package com.example.otelplus.service.impl;

import com.example.otelplus.dto.OdaDto;
import com.example.otelplus.dto.OtelDetayDto;
import com.example.otelplus.dto.OtelDto;
import com.example.otelplus.dto.OtelYorumDto;
import com.example.otelplus.model.Otel;
import com.example.otelplus.repository.IOtelRepository;
import com.example.otelplus.service.IOtelService;
import org.springframework.stereotype.Service;
import com.example.otelplus.dto.OtelArama;


import java.util.List;

@Service
public class OtelService implements IOtelService {

    private final IOtelRepository otelRepository;

    public OtelService(IOtelRepository otelRepository) {
        this.otelRepository = otelRepository;
    }

    // ---------------------------------------------------------
    // 1) Listeleme için OtelDto
    // ---------------------------------------------------------
    @Override
    public List<OtelDto> getAllOtels() {
        return otelRepository.findAll()
                .stream()
                .map(this::toOtelDto)
                .toList();
    }

    // ---------------------------------------------------------
    // 2) Detay için OtelDetayDto
    // ---------------------------------------------------------
    @Override
    public OtelDetayDto getOtelById(Integer id) {
        Otel otel = otelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Otel bulunamadı: " + id));
        return toOtelDetayDto(otel);
    }


    // =========================================================
    // DTO MAPPING METOTLARI
    // =========================================================

    // ------------------ OtelDto ------------------
    private OtelDto toOtelDto(Otel otel) {
        OtelDto dto = new OtelDto();
        dto.setOtelId(otel.getOtelId());
        dto.setOtelAdi(otel.getOtelAdi());
        dto.setSehir(otel.getSehir());
        dto.setOrtalamaPuan(hesaplaOrtalamaPuan(otel));
        return dto;
    }

    // ------------------ OtelDetayDto ------------------
    private OtelDetayDto toOtelDetayDto(Otel otel) {
        OtelDetayDto dto = new OtelDetayDto();

        dto.setOtelId(otel.getOtelId());
        dto.setOtelAdi(otel.getOtelAdi());
        dto.setAdres(otel.getAdres());
        dto.setSehir(otel.getSehir());
        dto.setEnlem(otel.getEnlem());
        dto.setBoylam(otel.getBoylam());

        // Özellikler → sade liste
        dto.setOzellikler(
                otel.getOzellikler()
                        .stream()
                        .map(oz -> oz.getOzellikAdi())
                        .toList()
        );

        // Odalar → OdaDto listesi
        dto.setOdalar(
                otel.getOdalar()
                        .stream()
                        .map(oda -> new OdaDto(
                                oda.getOdaId(),
                                oda.getOdaTipi(),
                                oda.getKapasite(),
                                oda.getFiyat()
                        ))
                        .toList()
        );

        // Yorumlar → OtelYorumDto listesi
        dto.setYorumlar(
                otel.getYorumlar()
                        .stream()
                        .map(y -> new OtelYorumDto(
                                y.getYorumId(),
                                y.getKullaniciAdi(),
                                y.getYorumIcerigi(),
                                y.getPuan(),
                                y.getYorumTarihi()
                        ))
                        .toList()
        );

        // Ortalama puan → hesaplanmış değer
        dto.setOrtalamaPuan(hesaplaOrtalamaPuan(otel));

        return dto;
    }


    // ---------------------------------------------------------
    // Ortalama puan hesaplayan yardımcı metot
    // ---------------------------------------------------------
    private Double hesaplaOrtalamaPuan(Otel otel) {
        if (otel.getYorumlar() == null || otel.getYorumlar().isEmpty()) {
            return 0.0;
        }

        return otel.getYorumlar()
                .stream()
                .mapToDouble(y -> y.getPuan())
                .average()
                .orElse(0.0);
    }
    @Override
    public List<OtelDto> searchBySehirFn(String sehir, String sort) {
        return otelRepository.searchBySehirFn(sehir, sort)
                .stream()
                .map(p -> {
                    OtelDto dto = new OtelDto();
                    dto.setOtelId(p.getOtel_id());
                    dto.setOtelAdi(p.getOtel_adi());
                    dto.setSehir(p.getSehir());
                    dto.setOrtalamaPuan(
                            p.getOrt_puan() != null ? p.getOrt_puan().doubleValue() : 0.0
                    );
                    dto.setMinFiyat(
                            p.getMin_fiyat() != null ? p.getMin_fiyat().doubleValue() : 0.0
                    );
                    return dto;
                })
                .toList();
    }
}
