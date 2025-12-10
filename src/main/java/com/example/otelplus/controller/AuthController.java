package com.example.otelplus.controller;

import com.example.otelplus.dto.auth.AuthResponseDTO;
import com.example.otelplus.dto.auth.LoginRequestDTO;
import com.example.otelplus.dto.auth.RegisterRequestDTO;
import com.example.otelplus.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequestDTO requestDTO) {
        authService.register(requestDTO);
        return new ResponseEntity<>("Kullanıcı kaydı başarıyla tamamlandı.", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO requestDTO) {
        AuthResponseDTO response = authService.login(requestDTO);
        return ResponseEntity.ok(response);
    }
}