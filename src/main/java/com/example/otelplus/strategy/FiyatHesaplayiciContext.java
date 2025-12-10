package com.example.otelplus.strategy;



import com.example.otelplus.model.Oda;

public class FiyatHesaplayiciContext {

    private FiyatHesaplama strategy;

    public FiyatHesaplayiciContext(FiyatHesaplama strategy) {
        this.strategy = strategy;
    }

    public double hesapla(Oda oda) {
        return strategy.hesapla(oda);
    }
}
