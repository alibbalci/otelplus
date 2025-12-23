package com.example.otelplus.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.otelplus.dto.EtkinlikDto;
import com.example.otelplus.mapper.EtkinlikMapper;
import com.example.otelplus.repository.EtkinlikRepository;
import com.example.otelplus.service.EtkinlikService;

@Service
public class EtkinlikServiceImpl implements EtkinlikService {

    private final EtkinlikRepository repository;

    public EtkinlikServiceImpl(EtkinlikRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<EtkinlikDto> getBySehir(Integer sehirId) {
        return repository.findBySehir_SehirId(sehirId)
                .stream()
                .map(EtkinlikMapper::toDto)
                .toList();
    }
}
