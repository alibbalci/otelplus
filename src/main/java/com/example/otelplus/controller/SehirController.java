package com.example.otelplus.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.otelplus.dto.SehirDto;
import com.example.otelplus.service.SehirService;

@RestController
@RequestMapping("/api/sehirler")
public class SehirController {

    private final SehirService sehirService;

    public SehirController(SehirService sehirService) {
        this.sehirService = sehirService;
    }

    @GetMapping
    public List<SehirDto> getAll() {
        return sehirService.getAll();
    }

    @GetMapping("/{id}")
    public SehirDto getById(@PathVariable Integer id) {
        return sehirService.getById(id);
    }
}
