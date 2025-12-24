package com.example.otelplus.observer;

import com.example.otelplus.model.Rezervasyon;

public interface RezervasyonObserver {
    void onRezervasyonCreated(Rezervasyon rezervasyon);
}
