package com.example.otelplus.strategy;

import com.example.otelplus.model.Oda;

public class DeluxeOda implements FiyatHesaplama{

    @Override
    public double hesapla(Oda oda) {
        return 1750.0;
    }
}
