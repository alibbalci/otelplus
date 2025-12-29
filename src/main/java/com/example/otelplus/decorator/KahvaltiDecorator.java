package com.example.otelplus.decorator;

import java.math.BigDecimal;

public class KahvaltiDecorator extends FiyatDecorator {

    private static final BigDecimal KAHVALTI_UCRETI = BigDecimal.valueOf(250); // 250 TL

    public KahvaltiDecorator(IFiyatlandirma wrapped) {
        super(wrapped);
    }

    @Override
    public BigDecimal hesapla() {
        System.out.println("--- Kahvalti Eklendi (+250 TL) ---");
        return super.hesapla().add(KAHVALTI_UCRETI);
    }
}
