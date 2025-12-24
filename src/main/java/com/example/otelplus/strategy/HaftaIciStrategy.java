package com.example.otelplus.strategy;

import java.math.BigDecimal;

public class HaftaIciStrategy implements FiyatHesaplama
{
    @Override
    public BigDecimal hesapla(BigDecimal temelFiyat) {
        return temelFiyat;
    }
}
