package com.example.otelplus.service;

import com.example.otelplus.dto.auth.AuthResponseDTO;
import com.example.otelplus.dto.auth.LoginRequestDTO;
import com.example.otelplus.dto.auth.RegisterRequestDTO;

public interface AuthService {

    void register(RegisterRequestDTO requestDTO);

    AuthResponseDTO login(LoginRequestDTO requestDTO);
}