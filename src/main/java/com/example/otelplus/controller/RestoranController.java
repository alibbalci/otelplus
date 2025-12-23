package com.example.otelplus.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping
    public List<RestoranDto> getAll() {
        return service.getAll();
    }
}
