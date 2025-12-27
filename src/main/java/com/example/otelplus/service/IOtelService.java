package com.example.otelplus.service;

import com.example.otelplus.dto.OtelDetayDto;
import com.example.otelplus.dto.OtelDto;
import com.example.otelplus.model.Otel;

import java.util.List;

public interface IOtelService {

    List<OtelDto> getAllOtels();
    OtelDetayDto getOtelById(Integer id);
    List<OtelDto> searchBySehirFn(String sehir, String sort);
    void deleteById(Integer id);
    OtelDto otelEkle(OtelDto dto);
}
