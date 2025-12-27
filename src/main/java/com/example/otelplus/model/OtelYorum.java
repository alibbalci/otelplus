package com.example.otelplus.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "otel_yorum")
@Getter @Setter
public class OtelYorum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "yorum_id")
    private Integer yorumId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kullanici_id", nullable = false)
    private Kullanici kullanici;   // ✅ ilişki

    @Column(name = "yorum_icerigi", nullable = false, columnDefinition = "TEXT")
    private String yorumIcerigi;

    @Column(name = "puan", nullable = false)
    private Double puan;

    @Column(name = "yorum_tarihi")
    private LocalDateTime yorumTarihi = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "otel_id", nullable = false)
    private Otel otel;
}
