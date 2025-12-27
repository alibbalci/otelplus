package com.example.otelplus.controller;

import com.example.otelplus.dto.GenelDetayliRaporDto;
import com.example.otelplus.dto.SehirOzetRaporDto;
import com.example.otelplus.dto.OtelDto;
import com.example.otelplus.service.IOtelService;
import com.example.otelplus.service.IRaporService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final IOtelService otelService;
    private final IRaporService raporService;

    // 🏨 Admin → Otel ekle
    @PostMapping("/oteller")
    public ResponseEntity<OtelDto> otelEkle(@RequestBody OtelDto dto) {
        return ResponseEntity.ok(otelService.otelEkle(dto));
    }


    @DeleteMapping("/oteller/{id}")
    public ResponseEntity<Void> otelSil(@PathVariable Integer id) {
        otelService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // 📊 Admin → Genel detaylı rapor
    @GetMapping("/raporlar/genel")
    public ResponseEntity<List<GenelDetayliRaporDto>> genelRapor(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate baslangic,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate bitis) {

        return ResponseEntity.ok(
                raporService.genel(baslangic, bitis)
        );
    }

    // 📊 Admin → Şehir özet rapor
    @GetMapping("/raporlar/sehir")
    public ResponseEntity<List<SehirOzetRaporDto>> sehirOzetRapor(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate baslangic,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate bitis) {

        return ResponseEntity.ok(
                raporService.sehir(baslangic, bitis)
        );
    }
}
