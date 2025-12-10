package com.example.otelplus.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="otel")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Otel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="otel_id")
    private Integer otelId;

    @Column(name="otel_adi",nullable = false,unique = true)
    private String otelAdi;

    @Column(name="adres",nullable = false)
    private String adres;

    @Column(name="telefon",nullable = false,unique = true)
    private String telefon;

    @Column (name="sehir")
    private String sehir;

    @Column (name="enlem")
    private Double enlem;

    @Column (name="boylam")
    private Double boylam;

    @OneToMany (mappedBy = "otel", cascade = CascadeType.ALL , orphanRemoval = true, fetch=FetchType.LAZY)
    private List<Oda> odalar;

    @OneToMany (mappedBy = "otel", cascade = CascadeType.ALL , orphanRemoval = true, fetch=FetchType.LAZY)
    private List<OtelOzellik> ozellikler ;

    @OneToMany (mappedBy = "otel", cascade = CascadeType.ALL , orphanRemoval = true, fetch=FetchType.LAZY)
    private List<OtelYorum> yorumlar;

}
