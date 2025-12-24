package com.example.otelplus.observer;

import com.example.otelplus.model.Rezervasyon;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RezervasyonSubject {

    private final List<RezervasyonObserver> observers;

    public RezervasyonSubject(List<RezervasyonObserver> observers) {
        this.observers = observers;
    }

    public void notifyObservers(Rezervasyon r) {
        observers.forEach(o -> o.onRezervasyonCreated(r));
    }
}
