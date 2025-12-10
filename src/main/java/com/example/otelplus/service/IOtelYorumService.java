package com.example.otelplus.service;

import com.example.otelplus.dto.OtelYorumDto;
import com.example.otelplus.dto.OtelYorumDtoIU;
import com.example.otelplus.model.OtelYorum;

import java.util.List;

public interface IOtelYorumService {

    List<OtelYorumDto>  getYorumlarByOtelId(Integer otelId);
    OtelYorumDto addYorum(OtelYorumDtoIU otelYorumDtoIU);

}
