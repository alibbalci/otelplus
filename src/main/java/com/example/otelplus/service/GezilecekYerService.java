package com.example.otelplus.service;

import com.example.otelplus.dto.GezilecekYerDto;
import java.util.List;

public interface GezilecekYerService {
    List<GezilecekYerDto> getBySehirId(Integer sehirId);
}
