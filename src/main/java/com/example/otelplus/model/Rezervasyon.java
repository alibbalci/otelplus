package com.example.otelplus.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "rezervasyonlar")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rezervasyon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rezervasyon_id")
    private Integer rezervasyonId;

    @Column(name = "kullanici_id", nullable = false)
    private Integer kullaniciId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oda_id", nullable = false)
    private Oda oda;

    @Column(name = "giris_tarihi", nullable = false)
    private LocalDate girisTarihi;

    @Column(name = "cikis_tarihi", nullable = false)
    private LocalDate cikisTarihi;

    @Column(name = "rezervasyon_maliyeti", nullable = false)
    private Double rezervasyonMaliyeti;
}
