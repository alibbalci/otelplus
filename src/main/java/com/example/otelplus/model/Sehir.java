package com.example.otelplus.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "sehir")
@Getter @Setter
public class Sehir {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sehir_id")
    private Integer sehirId;

    @Column(name = "adi", nullable = false, unique = true)
    private String adi;
}
