package com.example.otelplus.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "otel_ozellik")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class OtelOzellik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ozellik_id")
    private Integer ozellikId;

    @Column(name="ozellik_adi",nullable = false)
    private String ozellikAdi;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "otel_id",nullable = false)
    private Otel otel;
}
