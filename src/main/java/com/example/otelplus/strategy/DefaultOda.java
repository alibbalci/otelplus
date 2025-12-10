package com.example.otelplus.strategy;

import com.example.otelplus.model.Oda;

public class DefaultOda implements FiyatHesaplama{
    @Override
    public double hesapla(Oda oda) {
        return 1100.0;
    }
}
