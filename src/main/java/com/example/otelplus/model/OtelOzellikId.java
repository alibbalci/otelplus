package com.example.otelplus.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OtelOzellikId implements Serializable {

    @Column(name = "otel_id")
    private Integer otelId;

    @Column(name = "ozellik_id")
    private Integer ozellikId;
}
