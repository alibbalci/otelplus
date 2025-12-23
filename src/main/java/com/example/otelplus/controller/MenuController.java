package com.example.otelplus.controller;

import com.example.otelplus.dto.MenuDto;
import com.example.otelplus.service.MenuService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menuler")
public class MenuController {

    private final MenuService service;

    public MenuController(MenuService service) {
        this.service = service;
    }

    @GetMapping("/restoran/{id}")
    public List<MenuDto> getByRestoran(@PathVariable Integer id) {
        return service.getByRestoranId(id);
    }
}
