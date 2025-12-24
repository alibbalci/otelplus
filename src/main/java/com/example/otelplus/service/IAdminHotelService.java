package com.example.otelplus.service;

public interface IAdminHotelService {
    // DİKKAT: Long yerine Integer yaptık
    String deleteHotelById(Integer hotelId, String userRole);
}