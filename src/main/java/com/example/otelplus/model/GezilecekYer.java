package com.example.otelplus.model;

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
@Table(name = "gezilecek_yer")
@Getter @Setter
public class GezilecekYer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "yer_id")
    private Integer yerId;

    @Column(name = "yer_adi", nullable = false)
    private String yerAdi;

    @Column(name = "yer_icerigi")
    private String yerIcerigi;

    @ManyToOne
    @JoinColumn(name = "sehir_id", nullable = false)
    private Sehir sehir;
}
