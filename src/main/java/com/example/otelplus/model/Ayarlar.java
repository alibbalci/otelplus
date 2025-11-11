package com.example.otelplus.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ayarlar")
public class Ayarlar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ayarlar_id")
    private Integer ayarlarId;

    @Column(name = "tema", length = 20)
    private String tema;

    @Column(name = "bildirim")
    private Boolean bildirim;

    // ----- İLİŞKİ SAHİBİ (Adım 6) -----
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kullanici_id", referencedColumnName = "kullanici_id", nullable = false, unique = true)
    private Kullanici kullanici;
}