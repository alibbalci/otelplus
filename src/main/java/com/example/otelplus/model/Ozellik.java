package com.example.otelplus.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ozellik")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ozellik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ozellik_id")
    private Integer ozellikId;

    @Column(name = "ozellik_adi", nullable = false, unique = true)
    private String ozellikAdi;
}
