package com.example.otelplus.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "etkinlik")
@Getter @Setter
public class Etkinlik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "etkinlik_id")
    private Integer etkinlikId;

    @Column(name = "etkinlik_adi", nullable = false)
    private String etkinlikAdi;

    @Column(name = "etkinlik_turu", nullable = false)
    private String etkinlikTuru; // sehir_ici | otel_ici

    @Column(name = "tarihi", nullable = false)
    private LocalDate tarihi;

    @Column(name = "icerigi")
    private String icerigi;

    @ManyToOne
    @JoinColumn(name = "sehir_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Sehir sehir;

    @Column(name = "otel_id")
    private Integer otelId;
    
}
