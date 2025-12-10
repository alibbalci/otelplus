package com.example.otelplus.controller;

import com.example.otelplus.dto.OdaDto;
import com.example.otelplus.service.IOdaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/odalar")
public class OdaController {

    private final IOdaService odaService;

    public OdaController(IOdaService odaService) {
        this.odaService = odaService;
    }

    // ---------------------------------------------------------
    // 1) Bir otele ait tüm odalar
    // ---------------------------------------------------------
    @GetMapping("/otel/{otelId}")
    public List<OdaDto> getOdalarByOtelId(@PathVariable Integer otelId) {
        return odaService.getOdalarByOtelId(otelId);
    }

    // ---------------------------------------------------------
    // 2) Oda detay (rezervasyon için)
    // ---------------------------------------------------------
    @GetMapping("/{odaId}")
    public OdaDto getOdaById(@PathVariable Integer odaId) {
        return odaService.getOdaById(odaId);
    }
}
