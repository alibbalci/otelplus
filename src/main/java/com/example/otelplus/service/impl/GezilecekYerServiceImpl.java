package com.example.otelplus.service.impl;

import com.example.otelplus.dto.GezilecekYerDto;
import com.example.otelplus.mapper.GezilecekYerMapper;
import com.example.otelplus.repository.GezilecekYerRepository;
import com.example.otelplus.service.GezilecekYerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GezilecekYerServiceImpl implements GezilecekYerService {

    private final GezilecekYerRepository repository;

    public GezilecekYerServiceImpl(GezilecekYerRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<GezilecekYerDto> getBySehirId(Integer sehirId) {
        return repository.findBySehir_SehirId(sehirId)
                .stream()
                .map(GezilecekYerMapper::toDto)
                .toList();
    }
}
