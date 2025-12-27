package com.example.otelplus.service.impl;

import com.example.otelplus.dto.GenelDetayliRaporDto;
import com.example.otelplus.dto.SehirOzetRaporDto;
import com.example.otelplus.repository.RaporRepository;
import com.example.otelplus.service.IRaporService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RaporServiceImpl implements IRaporService {

    private final RaporRepository raporRepository;

    @Override
    //@PreAuthorize("hasRole('ADMIN')")
    public List<GenelDetayliRaporDto> genel(LocalDate bas, LocalDate bit) {
        return raporRepository.genelDetayli(bas, bit).stream().map(r -> {
            GenelDetayliRaporDto d = new GenelDetayliRaporDto();
            d.setSehirAdi((String) r[0]);
            d.setToplamOtel(((Number) r[1]).longValue());
            d.setToplamOda(((Number) r[2]).longValue());
            d.setToplamRezervasyon(((Number) r[3]).longValue());
            d.setToplamCiro((BigDecimal) r[4]);
            d.setOrtalamaRezTutar((BigDecimal) r[5]);
            d.setOrtalamaKalisGece((BigDecimal) r[6]);
            d.setEnCokTercihEdilenOtel((String) r[7]);
            d.setEnCokTercihEdilenOdaTipi((String) r[8]);
            d.setSehirSiralama(((Number) r[9]).longValue());
            return d;
        }).toList();
    }

    @Override
    //@PreAuthorize("hasRole('ADMIN')")
    public List<SehirOzetRaporDto> sehir(LocalDate bas, LocalDate bit) {
        return raporRepository.sehirOzet(bas, bit).stream().map(r -> {
            SehirOzetRaporDto d = new SehirOzetRaporDto();
            d.setSehirAdi((String) r[0]);
            d.setToplamRezervasyon(((Number) r[1]).longValue());
            d.setToplamCiro((BigDecimal) r[2]);
            d.setOrtalamaKalisGece((BigDecimal) r[3]);
            d.setEnCokOdaTipi((String) r[4]);
            d.setEnCokOdaOrtalamaFiyat((BigDecimal) r[5]);
            return d;
        }).toList();
    }
}
