package com.example.otelplus.controller;


import com.example.otelplus.dto.OtelYorumDto;
import com.example.otelplus.dto.OtelYorumDtoIU;
import com.example.otelplus.service.IOtelYorumService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/yorumlar")
public class OtelYorumController {

    private final IOtelYorumService yorumService;

    public OtelYorumController(IOtelYorumService yorumService) {
        this.yorumService = yorumService;
    }

    // ---------------------------------------------------------
    // 1) Bir otele ait tüm yorumlar
    // ---------------------------------------------------------
    @GetMapping("/otel/{otelId}")
    public List<OtelYorumDto> getYorumlarByOtelId(@PathVariable Integer otelId) {
        return yorumService.getYorumlarByOtelId(otelId);
    }

    // ---------------------------------------------------------
    // 2) Yeni yorum ekleme
    // ---------------------------------------------------------
    @PostMapping("/otel/{otelId}/yorumekle")
    public OtelYorumDto addYorum(@RequestBody OtelYorumDtoIU dtoIU) {
        return yorumService.addYorum(dtoIU);
    }
}
