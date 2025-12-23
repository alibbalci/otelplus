package com.example.otelplus.service;

import java.util.List;
import com.example.otelplus.dto.RestoranDto;

public interface RestoranService {

    // Tüm restoranları getir
    List<RestoranDto> getAll();

    // Sadece belirli bir otelin restoranlarını getir
    List<RestoranDto> getRestoranlarByOtelId(Integer otelId);
}