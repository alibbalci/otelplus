package com.example.otelplus.strategy;

import com.example.otelplus.model.Oda;

public class StandartOda implements FiyatHesaplama{
    @Override
    public double hesapla(Oda oda) {
        return 1000.0;
    }
}
