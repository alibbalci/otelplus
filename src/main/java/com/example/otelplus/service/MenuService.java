package com.example.otelplus.service;

import com.example.otelplus.dto.MenuDto;
import java.util.List;

public interface MenuService {
    List<MenuDto> getByRestoranId(Integer restoranId);
}
