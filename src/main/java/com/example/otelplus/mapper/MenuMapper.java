package com.example.otelplus.mapper;

import com.example.otelplus.dto.MenuDto;
import com.example.otelplus.model.Menu;

public class MenuMapper {

    public static MenuDto toDto(Menu menu) {
        return new MenuDto(
                menu.getMenuId(),
                menu.getUrunAdi(),
                menu.getResimYolu(),
                menu.getOgunTuru()
        );
    }
}
