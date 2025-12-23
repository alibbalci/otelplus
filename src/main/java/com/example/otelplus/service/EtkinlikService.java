package com.example.otelplus.service;

import java.util.List;

import com.example.otelplus.dto.EtkinlikDto;

public interface EtkinlikService {
    List<EtkinlikDto> getBySehir(Integer sehirId);
}
