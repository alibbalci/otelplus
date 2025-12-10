package com.example.otelplus.service.impl;

import com.example.otelplus.dto.OdaDto;
import com.example.otelplus.model.Oda;
import com.example.otelplus.repository.IOdaRepository;
import com.example.otelplus.service.IOdaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OdaService implements IOdaService {

    private final IOdaRepository odaRepository;

    public OdaService(IOdaRepository odaRepository) {
        this.odaRepository = odaRepository;
    }

    @Override
    public List<OdaDto> getOdalarByOtelId(Integer otelId) {
        List<Oda> odalar = odaRepository.findByOtel_OtelId(otelId);

        return odalar.stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public OdaDto getOdaById(Integer odaId) {
        Oda oda = odaRepository.findById(odaId)
                .orElseThrow(() -> new RuntimeException("Oda bulunamadı: " + odaId));

        return toDto(oda);
    }

    // ------------------ MAPPING ------------------
    private OdaDto toDto(Oda oda) {
        OdaDto dto = new OdaDto();
        dto.setOdaId(oda.getOdaId());
        dto.setOdaTipi(oda.getOdaTipi());
        dto.setKapasite(oda.getKapasite());
        return dto;
    }
}
