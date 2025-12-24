package com.example.otelplus.strategy;

import java.math.BigDecimal;

public class HaftaSonuStrategy implements FiyatHesaplama {
    @Override
    public BigDecimal hesapla(BigDecimal temelFiyat) {
        return temelFiyat.multiply(BigDecimal.valueOf(1.20)); // %20 zam
    }
}
