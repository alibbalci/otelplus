package com.example.otelplus.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.otelplus.dto.GezilecekYerDto;
import com.example.otelplus.service.GezilecekYerService;

@RestController
@RequestMapping("/api/gezilecekyerler")
public class GezilecekYerController {

    private final GezilecekYerService service;

    public GezilecekYerController(GezilecekYerService service) {
        this.service = service;
    }

    @GetMapping("/sehir/{sehirId}")
    public List<GezilecekYerDto> getBySehir(@PathVariable Integer sehirId) {
        return service.getBySehirId(sehirId);
    }
}
