package com.example.otelplus.strategy;

import java.math.BigDecimal;

public interface FiyatHesaplama {
    BigDecimal hesapla(BigDecimal temelFiyat);
}
