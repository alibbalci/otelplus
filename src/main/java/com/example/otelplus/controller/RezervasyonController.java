package com.example.otelplus.controller;

import com.example.otelplus.dto.RezervasyonDto;
import com.example.otelplus.dto.RezervasyonDtoIU;
import com.example.otelplus.service.IRezervasyonService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rezervasyonlar")
public class RezervasyonController {

    private final IRezervasyonService rezervasyonService;

    public RezervasyonController(IRezervasyonService rezervasyonService) {
        this.rezervasyonService = rezervasyonService;
    }

    // --------------------------------------------------------
    // 1) Yeni rezervasyon oluştur
    @PostMapping("/rezervasyon")
    public RezervasyonDto createRezervasyon(@RequestBody RezervasyonDtoIU dtoIU) {
        return rezervasyonService.createRezervasyon(dtoIU); // Otomatik 200 OK döner
    }

    // --------------------------------------------------------
    // 2) ID ile rezervasyon getir
    // --------------------------------------------------------
    @GetMapping("rezervasyon/{id}")
    public RezervasyonDto getByRezervasyonId(@PathVariable Integer id) {
        return rezervasyonService.getById(id); // Otomatik olarak 200 OK ve JSON döner
    }

    // --------------------------------------------------------
    // 3) Kullanıcıya ait tüm rezervasyonlar (Profil ekranı için)
    // --------------------------------------------------------


    @GetMapping("/kullanici/{kullaniciId}")
    public List<RezervasyonDto> getByKullanici(@PathVariable Integer kullaniciId) {
        return rezervasyonService.getByKullaniciId(kullaniciId);
    }

    // --------------------------------------------------------
    // 4) Rezervasyon iptali (delete)
    // --------------------------------------------------------
    @DeleteMapping("delete/{id}")
    public void deleteRezervasyon(@PathVariable Integer id) {
        rezervasyonService.delete(id);
    }

    // --------------------------------------------------------
    // 5) Rezervasyon tarih güncelleme (opsiyonel)
    // --------------------------------------------------------
    @PutMapping("/{id}")
    public RezervasyonDto update(
            @PathVariable Integer id,           // Adresten ID'yi kapar (Hangi rezervasyon?)
            @RequestBody RezervasyonDtoIU dtoIU // Kutudan veriyi alır (Yeni bilgiler ne?)
    ) {
        return rezervasyonService.update(id, dtoIU); // 200 OK ve güncel halini döner
    }
}
