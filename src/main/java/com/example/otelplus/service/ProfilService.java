package com.example.otelplus.service;

import com.example.otelplus.dto.profil.ProfilViewDTO;


public interface ProfilService {

    ProfilViewDTO getProfilByKullaniciId(Integer kullaniciId);

    
}