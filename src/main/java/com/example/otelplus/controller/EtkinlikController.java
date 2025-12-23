package com.example.otelplus.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.otelplus.dto.EtkinlikDto;
import com.example.otelplus.service.EtkinlikService;

@RestController
@RequestMapping("/api/etkinlikler")
public class EtkinlikController {

    private final EtkinlikService service;

    public EtkinlikController(EtkinlikService service) {
        this.service = service;
    }

    @GetMapping("/sehir/{id}")
    public List<EtkinlikDto> getBySehir(@PathVariable Integer id) {
        return service.getBySehir(id);
    }
}
