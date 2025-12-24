package com.example.otelplus.dto;

import java.math.BigDecimal;

public interface OtelArama {
    Integer getOtel_id();
    String getOtel_adi();
    String getSehir();
    BigDecimal getOrt_puan();
    BigDecimal getMin_fiyat();
}
