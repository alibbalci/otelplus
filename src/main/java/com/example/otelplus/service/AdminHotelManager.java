package com.example.otelplus.service;

import org.springframework.beans.factory.annotation.Autowired; // İSİM DÜZELDİ
import org.springframework.stereotype.Service;

import com.example.otelplus.repository.IOtelRepository;

@Service
public class AdminHotelManager implements IAdminHotelService {

    @Autowired
    private IOtelRepository otelRepository; // TİP DÜZELDİ

    @Override
    public String deleteHotelById(Integer hotelId, String userRole) { // Integer OLDU
        
        if (otelRepository.existsById(hotelId)) {
            otelRepository.deleteById(hotelId);
            return "BAŞARILI: Otel (ID: " + hotelId + ") veritabanından silindi.";
        } else {
            throw new RuntimeException("HATA: Böyle bir otel bulunamadı!");
        }
    }
}