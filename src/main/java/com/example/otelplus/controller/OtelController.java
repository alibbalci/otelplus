package com.example.otelplus.controller;

import com.example.otelplus.dto.OtelDto;
import com.example.otelplus.dto.OtelDetayDto;
import com.example.otelplus.service.IOtelService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/oteller")
public class OtelController {

    private final IOtelService otelService;

    public OtelController(IOtelService otelService) {
        this.otelService = otelService;
    }

    // ----------------------------------------------------
    // 1) Tüm otelleri listele (otel kartları için)
    // ----------------------------------------------------
    @GetMapping
    public List<OtelDto> getAllOtels() {
        return otelService.getAllOtels();
    }

    // ----------------------------------------------------
    // 2) ID ile detay (otel detay sayfası için)
    // ----------------------------------------------------
    @GetMapping("/{id}")
    public OtelDetayDto getOtelById(@PathVariable Integer id) {
        return otelService.getOtelById(id);
    }
}
