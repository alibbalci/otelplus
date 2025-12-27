package com.example.otelplus.controller;

import com.example.otelplus.dto.OtelDto;
import com.example.otelplus.dto.OtelDetayDto;
import com.example.otelplus.service.IOtelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/oteller")
public class OtelController {

    private final IOtelService otelService;

    public OtelController(IOtelService otelService) {
        this.otelService = otelService;
    }

    @GetMapping
    public List<OtelDto> getAllOtels() {
        return otelService.getAllOtels();
    }

    @GetMapping("/{id}")
    public OtelDetayDto getOtelById(@PathVariable Integer id) {
        return otelService.getOtelById(id);
    }

    @GetMapping("/search-fn")
    public List<OtelDto> searchWithFunction(
            @RequestParam(required = false) String sehir,
            @RequestParam(defaultValue = "puan_desc") String sort) {

        return otelService.searchBySehirFn(sehir, sort);
    }

    // ✅ DELETE: /api/oteller/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOtel(@PathVariable Integer id,
                                           @RequestParam String role) {
        if (!"ADMIN".equalsIgnoreCase(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        otelService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
