package com.example.otelplus.strategy;

import java.math.BigDecimal;

public class KisSezonuStrategy implements FiyatHesaplama {
    @Override
    public BigDecimal hesapla(BigDecimal temelFiyat) {
        return temelFiyat.multiply(BigDecimal.valueOf(0.85)); // %15 indirim
    }
}
