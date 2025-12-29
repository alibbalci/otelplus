package com.example.otelplus.decorator;

import java.math.BigDecimal;

public class TemelFiyat implements IFiyatlandirma {

    private final BigDecimal tabanFiyat;

    public TemelFiyat(BigDecimal tabanFiyat) {
        this.tabanFiyat = tabanFiyat;
    }

    @Override
    public BigDecimal hesapla() {
        return tabanFiyat;
    }
}
