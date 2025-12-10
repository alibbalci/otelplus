package com.example.otelplus.service;

import com.example.otelplus.dto.OdaDto;
import com.example.otelplus.model.Oda;

import java.util.List;

public interface IOdaService {

    List<OdaDto> getOdalarByOtelId(Integer otelId);
    OdaDto getOdaById(Integer odaId);

}
