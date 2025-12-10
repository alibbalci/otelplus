package com.example.otelplus.service.impl;

import com.example.otelplus.dto.OtelYorumDto;
import com.example.otelplus.dto.OtelYorumDtoIU;
import com.example.otelplus.model.Otel;
import com.example.otelplus.model.OtelYorum;
import com.example.otelplus.repository.IOtelRepository;
import com.example.otelplus.repository.IYorumRepository;
import com.example.otelplus.service.IOtelYorumService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OtelYorumService implements IOtelYorumService {

    private final IYorumRepository yorumRepository;
    private final IOtelRepository otelRepository;

    public OtelYorumService(IYorumRepository yorumRepository,
                            IOtelRepository otelRepository) {
        this.yorumRepository = yorumRepository;
        this.otelRepository = otelRepository;
    }

    @Override
    public List<OtelYorumDto> getYorumlarByOtelId(Integer otelId) {
        List<OtelYorum> yorumlar = yorumRepository.findByOtel_OtelId(otelId);

        return yorumlar.stream()
                .map(this::toDto)
                .toList();
    }

    private OtelYorumDto toDto(OtelYorum yorum) {
        OtelYorumDto dto = new OtelYorumDto();
        dto.setYorumId(yorum.getYorumId());
        dto.setKullaniciAdi(yorum.getKullaniciAdi());
        dto.setYorumIcerigi(yorum.getYorumIcerigi());
        dto.setPuan(yorum.getPuan());
        dto.setYorumTarihi(yorum.getYorumTarihi());
        return dto;
    }

    @Override
    public OtelYorumDto addYorum(OtelYorumDtoIU dtoIU) {

        // 1 — OTEL VAR MI KONTROL
        Otel otel = otelRepository.findById(dtoIU.getOtelId())
                .orElseThrow(() -> new RuntimeException("Otel bulunamadı: " + dtoIU.getOtelId()));

        // 2 — ENTITY OLUŞTUR
        OtelYorum yorum = new OtelYorum();
        yorum.setOtel(otel);
        yorum.setKullaniciAdi(dtoIU.getKullaniciAdi());
        yorum.setYorumIcerigi(dtoIU.getYorumIcerigi());
        yorum.setPuan(dtoIU.getPuan());

        // 3 — KAYDET
        OtelYorum saved = yorumRepository.save(yorum);

        // 4 — DTO DÖNDÜR
        return toDto(saved);
    }
}
