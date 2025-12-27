package com.example.otelplus.service;

import com.example.otelplus.dto.GenelDetayliRaporDto;
import com.example.otelplus.dto.SehirOzetRaporDto;

import java.time.LocalDate;
import java.util.List;

public interface IRaporService {
    List<GenelDetayliRaporDto> genel(LocalDate bas, LocalDate bit);
    List<SehirOzetRaporDto> sehir(LocalDate bas, LocalDate bit);
}
