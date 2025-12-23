package com.example.otelplus.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "menu")
@Getter @Setter
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Integer menuId;

    @Column(name = "urun_adi", nullable = false)
    private String urunAdi;

    @Column(name = "resim_yolu")
    private String resimYolu;

    @Column(name = "ogun_turu")
    private String ogunTuru;

    @Column(name = "olusturma_tarihi")
    private LocalDateTime olusturmaTarihi;

    @ManyToOne
    @JoinColumn(name = "restoran_id", nullable = false)
    private Restoran restoran;

    
}
