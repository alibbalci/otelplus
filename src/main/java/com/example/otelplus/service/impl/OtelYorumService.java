package com.example.otelplus.service.impl;

import com.example.otelplus.dto.OtelYorumDto;
import com.example.otelplus.dto.OtelYorumDtoIU;
import com.example.otelplus.model.Kullanici;
import com.example.otelplus.model.Otel;
import com.example.otelplus.model.OtelYorum;
import com.example.otelplus.repository.IOtelRepository;
import com.example.otelplus.repository.IYorumRepository;
import com.example.otelplus.repository.KullaniciRepository;
import com.example.otelplus.service.IOtelYorumService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OtelYorumService implements IOtelYorumService {

    private final IYorumRepository yorumRepository;
    private final IOtelRepository otelRepository;
    private final KullaniciRepository kullaniciRepository;

    public OtelYorumService(IYorumRepository yorumRepository,
                            IOtelRepository otelRepository,
                            KullaniciRepository kullaniciRepository) {
        this.yorumRepository = yorumRepository;
        this.otelRepository = otelRepository;
        this.kullaniciRepository = kullaniciRepository;
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
        dto.setKullaniciAdi(
                yorum.getKullanici() != null
                        ? yorum.getKullanici().getKullaniciAdi()
                        : null
        );
        dto.setYorumIcerigi(yorum.getYorumIcerigi());
        dto.setPuan(yorum.getPuan());
        dto.setYorumTarihi(yorum.getYorumTarihi());
        return dto;
    }

    @Override
    public OtelYorumDto addYorum(OtelYorumDtoIU dtoIU) {

        // 1 — OTEL VAR MI
        Otel otel = otelRepository.findById(dtoIU.getOtelId())
                .orElseThrow(() ->
                        new RuntimeException("Otel bulunamadı: " + dtoIU.getOtelId()));

        // 2 — KULLANICI VAR MI (ID ile)
        Kullanici kullanici = kullaniciRepository.findById(dtoIU.getKullaniciId())
                .orElseThrow(() ->
                        new RuntimeException("Kullanıcı bulunamadı: " + dtoIU.getKullaniciId()));

        // 3 — ENTITY OLUŞTUR
        OtelYorum yorum = new OtelYorum();
        yorum.setOtel(otel);
        yorum.setKullanici(kullanici);
        yorum.setYorumIcerigi(dtoIU.getYorumIcerigi());
        yorum.setPuan(dtoIU.getPuan());

        // 4 — KAYDET
        OtelYorum saved = yorumRepository.save(yorum);

        // 5 — DTO DÖNDÜR
        return toDto(saved);
    }
}
