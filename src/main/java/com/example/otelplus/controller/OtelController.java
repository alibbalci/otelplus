package com.example.otelplus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired; // Bu eksikse ekle
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.otelplus.dto.OtelDetayDto;
import com.example.otelplus.dto.OtelDto;
import com.example.otelplus.service.IAdminHotelService;
import com.example.otelplus.service.IOtelService;

@RestController
@RequestMapping("/api/oteller")
public class OtelController {

    private final IOtelService otelService;

    @Autowired
    private IAdminHotelService adminService;
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
    // --- YENİ EKLENEN SİLME ÖZELLİĞİ (PROXY) ---
    // Kullanımı: DELETE http://localhost:8080/api/oteller/1?role=ADMIN
    @DeleteMapping("/{id}")
    public String deleteOtel(@PathVariable Integer id, @RequestParam String role) { // Long yerine Integer
        return adminService.deleteHotelById(id, role);
    }
}
