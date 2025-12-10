package com.example.otelplus.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.otelplus.dto.profil.ProfilViewDTO;
import com.example.otelplus.model.Kullanici;
import com.example.otelplus.service.ProfilService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/profil")
@RequiredArgsConstructor
public class ProfilController {

    private final ProfilService profilService;

    @GetMapping("/profilim")
    public ResponseEntity<ProfilViewDTO> getKendiProfilim(@AuthenticationPrincipal Kullanici kullanici) {

        Integer kullaniciId = kullanici.getKullaniciId();

        ProfilViewDTO profil = profilService.getProfilByKullaniciId(kullaniciId);
        return ResponseEntity.ok(profil);
    }
}