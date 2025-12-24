package com.example.otelplus.strategy;




import java.math.BigDecimal;

public class FiyatHesaplayiciContext {

    private final FiyatHesaplama strategy;

    public FiyatHesaplayiciContext(FiyatHesaplama strategy) {
        this.strategy = strategy;
    }

    public BigDecimal hesapla(BigDecimal temelFiyat) {
        return strategy.hesapla(temelFiyat);
    }
}
