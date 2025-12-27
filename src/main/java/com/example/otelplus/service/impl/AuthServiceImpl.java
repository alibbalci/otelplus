package com.example.otelplus.service.impl;

import com.example.otelplus.dto.auth.AuthResponseDTO;
import com.example.otelplus.dto.auth.LoginRequestDTO;
import com.example.otelplus.dto.auth.RegisterRequestDTO;
import com.example.otelplus.exception.UserAlreadyExistsException;
import com.example.otelplus.model.Kullanici;
import com.example.otelplus.repository.KullaniciRepository;
import com.example.otelplus.security.JwtTokenProvider;
import com.example.otelplus.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final KullaniciRepository kullaniciRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional
    public void register(RegisterRequestDTO requestDTO) {

        if (kullaniciRepository.existsByKullaniciAdi(requestDTO.getKullaniciAdi())) {
            throw new UserAlreadyExistsException("Bu kullanıcı adı zaten alınmış: " + requestDTO.getKullaniciAdi());
        }

        if (kullaniciRepository.existsByKullaniciEposta(requestDTO.getKullaniciEposta())) {
            throw new UserAlreadyExistsException("Bu e-posta adresi zaten kayıtlı: " + requestDTO.getKullaniciEposta());
        }

        // 🔐 Şifre encode
        String encodedPassword = passwordEncoder.encode(requestDTO.getKullaniciSifre());

        // ✅ PostgreSQL procedure çağrısı
        kullaniciRepository.spKullaniciOlustur(
                requestDTO.getKullaniciAdi(),
                requestDTO.getKullaniciEposta(),
                encodedPassword,
                requestDTO.getKullaniciCinsiyet(),
                requestDTO.getIsim(),
                requestDTO.getSoyisim(),
                requestDTO.getDogumTarihi(),
                requestDTO.getUlke()
        );
    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO requestDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDTO.getKullaniciAdi(),
                        requestDTO.getKullaniciSifre()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        Kullanici kullanici = kullaniciRepository.findByKullaniciAdi(requestDTO.getKullaniciAdi())
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı: " + requestDTO.getKullaniciAdi()));

        return new AuthResponseDTO(token, requestDTO.getKullaniciAdi(), kullanici.getKullaniciId());
    }
}
