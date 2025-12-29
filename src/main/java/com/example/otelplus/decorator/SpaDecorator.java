package com.example.otelplus.decorator;

import java.math.BigDecimal;

public class SpaDecorator extends FiyatDecorator {

    private static final BigDecimal SPA_UCRETI = BigDecimal.valueOf(500); // 500 TL

    public SpaDecorator(IFiyatlandirma wrapped) {
        super(wrapped);
    }

    @Override
    public BigDecimal hesapla() {
        System.out.println("--- SPA Paketi Eklendi (+500 TL) ---");
        return super.hesapla().add(SPA_UCRETI);
    }
}
