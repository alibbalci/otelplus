package com.example.otelplus.service.impl;

import com.example.otelplus.dto.profil.ProfilViewDTO;
import com.example.otelplus.exception.ResourceNotFoundException;
import com.example.otelplus.model.Kullanici;
import com.example.otelplus.model.ProfilBilgileri;
import com.example.otelplus.repository.KullaniciRepository;
import com.example.otelplus.repository.ProfilRepository;
import com.example.otelplus.service.ProfilService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfilServiceImpl implements ProfilService {

    private final ProfilRepository profilRepository;
    private final KullaniciRepository kullaniciRepository;

    @Override
    public ProfilViewDTO getProfilByKullaniciId(Integer kullaniciId) {

        Kullanici kullanici = kullaniciRepository.findById(kullaniciId)
            .orElseThrow(() -> new ResourceNotFoundException("Kullanıcı bulunamadı ID: " + kullaniciId));

        ProfilBilgileri profil = profilRepository.findByKullanici_KullaniciId(kullaniciId)
            .orElseThrow(() -> new ResourceNotFoundException("Profil bulunamadı ID: " + kullaniciId));

        ProfilViewDTO dto = new ProfilViewDTO();
        dto.setIsim(profil.getIsim());
        dto.setSoyisim(profil.getSoyisim());
        dto.setDogumTarihi(profil.getDogumTarihi());
        dto.setUlke(profil.getUlke());
        dto.setResim(profil.getResim());
        dto.setKullaniciBiyografi(profil.getKullaniciBiyografi());
        dto.setKullaniciAdi(kullanici.getKullaniciAdi());
        dto.setKullaniciEposta(kullanici.getKullaniciEposta());

        return dto;
    }
}