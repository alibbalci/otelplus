package com.example.otelplus.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "otel_ozellik")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OtelOzellik {

    @EmbeddedId
    private OtelOzellikId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("otelId")
    @JoinColumn(name = "otel_id")
    private Otel otel;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("ozellikId")
    @JoinColumn(name = "ozellik_id")
    private Ozellik ozellik;
}
