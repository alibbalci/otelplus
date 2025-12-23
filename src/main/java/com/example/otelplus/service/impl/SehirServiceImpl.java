package com.example.otelplus.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.otelplus.dto.SehirDto;
import com.example.otelplus.exception.NotFoundException;
import com.example.otelplus.mapper.SehirMapper;
import com.example.otelplus.model.Sehir;
import com.example.otelplus.repository.SehirRepository;
import com.example.otelplus.service.SehirService;

@Service
public class SehirServiceImpl implements SehirService {

    private final SehirRepository sehirRepository;

    public SehirServiceImpl(SehirRepository sehirRepository) {
        this.sehirRepository = sehirRepository;
    }

    @Override
    public List<SehirDto> getAll() {
        return sehirRepository.findAll()
                .stream()
                .map(SehirMapper::toDto)
                .toList();
    }

    @Override
    public SehirDto getById(Integer id) {
        Sehir sehir = sehirRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Şehir bulunamadı"));
        return SehirMapper.toDto(sehir);
    }
}
