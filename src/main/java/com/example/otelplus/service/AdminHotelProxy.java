package com.example.otelplus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class AdminHotelProxy implements IAdminHotelService {

    @Autowired
    private AdminHotelManager realManager;

    @Override
    public String deleteHotelById(Integer hotelId, String userRole) { // Integer OLDU
        
        System.out.println("--- Proxy Devrede: Rol Kontrolü Yapılıyor ---");

        if ("ADMIN".equalsIgnoreCase(userRole)) {
            return realManager.deleteHotelById(hotelId, userRole);
        } else {
            throw new RuntimeException("ERİŞİM REDDEDİLDİ: Sadece ADMIN yetkisi olanlar silebilir!");
        }
    }
}