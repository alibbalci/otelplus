package com.example.otelplus.service.impl;

import com.example.otelplus.dto.RestoranDto;
import com.example.otelplus.mapper.RestoranMapper;
import com.example.otelplus.repository.RestoranRepository;
import com.example.otelplus.service.RestoranService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestoranServiceImpl implements RestoranService {

    private final RestoranRepository repository;

    public RestoranServiceImpl(RestoranRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<RestoranDto> getAll() {
        return repository.findAll()
                .stream()
                .map(RestoranMapper::toDto)
                .toList();
    }
}
