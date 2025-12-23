package com.example.otelplus.service;

import com.example.otelplus.dto.SehirDto;
import java.util.List;

public interface SehirService {
    List<SehirDto> getAll();
    SehirDto getById(Integer id);
}
