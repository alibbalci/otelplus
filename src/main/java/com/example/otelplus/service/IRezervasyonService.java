package com.example.otelplus.service;

import com.example.otelplus.dto.RezervasyonDto;
import com.example.otelplus.dto.RezervasyonDtoIU;

import java.util.List;

public interface IRezervasyonService {
    RezervasyonDto createRezervasyon(RezervasyonDtoIU dtoIU);


    RezervasyonDto getById(Integer id);

    List<RezervasyonDto> getByKullaniciId(Integer kullaniciId);

    void delete(Integer id);

    RezervasyonDto update(Integer id, RezervasyonDtoIU dtoIU);

}
