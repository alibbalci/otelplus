package com.example.otelplus.strategy;

import com.example.otelplus.model.Oda;

public class SuitOda implements FiyatHesaplama{
    @Override
    public double hesapla(Oda oda) {
        return 2500.0;
    }
}
