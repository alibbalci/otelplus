package com.example.otelplus.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.otelplus.dto.RestoranDto;
import com.example.otelplus.service.RestoranService;

@RestController
@RequestMapping("/api/restoranlar")
public class RestoranController {

    private final RestoranService service;

    public RestoranController(RestoranService service) {
        this.service = service;
    }

    // Tüm restoranları getirir
    @GetMapping
    public List<RestoranDto> getAll() {
        return service.getAll();
    }

    // Belirli bir otele ait restoranları getirir
    // Örnek: GET http://localhost:8080/api/restoranlar/otel/1
    @GetMapping("/otel/{otelId}")
    public List<RestoranDto> getRestoranlarByOtelId(@PathVariable Integer otelId) {
        return service.getRestoranlarByOtelId(otelId);
    }
}