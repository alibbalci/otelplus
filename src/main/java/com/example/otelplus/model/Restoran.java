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
@Table(name = "restoranlar")
@Getter @Setter
public class Restoran {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restoran_id")
    private Integer restoranId;

    @Column(name = "restoran_adi", nullable = false)
    private String restoranAdi;

    @Column(name = "calisma_saatleri")
    private String calismaSaatleri;

    @Column(name = "otel_id", nullable = false)
    private Integer otelId;
}
