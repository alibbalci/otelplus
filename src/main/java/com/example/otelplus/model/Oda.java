package com.example.otelplus.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "odalar")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Oda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer odaId;

    @Column(name = "oda_tipi", nullable = false)
    private String odaTipi;

    @Column(name = "oda_icerigi", nullable = false)
    private String odaIcerigi;

    @Column(name = "kapasite", nullable = false)
    private Integer kapasite;

    // 🔹 YENİ: Oda fiyatı
    @Column(name = "fiyat", nullable = false)
    private BigDecimal fiyat;

    // -- Otel İlişkisi
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "otel_id", nullable = false)
    private Otel otel;

    // -- Rezervasyon İlişkisi
    @OneToMany(mappedBy = "oda",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<Rezervasyon> rezervasyonlar;
}
