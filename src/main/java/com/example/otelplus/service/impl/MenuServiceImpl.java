package com.example.otelplus.service.impl;

import com.example.otelplus.dto.MenuDto;
import com.example.otelplus.mapper.MenuMapper;
import com.example.otelplus.repository.MenuRepository;
import com.example.otelplus.service.MenuService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    private final MenuRepository repository;

    public MenuServiceImpl(MenuRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<MenuDto> getByRestoranId(Integer restoranId) {
        return repository.findByRestoran_RestoranId(restoranId)
                .stream()
                .map(MenuMapper::toDto)
                .toList();
    }
}
