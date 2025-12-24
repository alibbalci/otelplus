package com.example.otelplus.strategy;

import java.math.BigDecimal;

public class YazSezonuStrategy implements FiyatHesaplama {
    @Override
    public BigDecimal hesapla(BigDecimal temelFiyat) {
        return temelFiyat.multiply(BigDecimal.valueOf(1.30)); // %30 zam
    }
}
