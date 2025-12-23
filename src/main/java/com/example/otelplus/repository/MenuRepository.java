package com.example.otelplus.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.otelplus.model.Menu;

public interface MenuRepository extends JpaRepository<Menu, Integer> {
    List<Menu> findByRestoran_RestoranId(Integer restoranId);
}
