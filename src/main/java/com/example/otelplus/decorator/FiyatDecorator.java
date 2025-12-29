package com.example.otelplus.decorator;

import java.math.BigDecimal;

public abstract class FiyatDecorator implements IFiyatlandirma {

    protected IFiyatlandirma wrapped;

    public FiyatDecorator(IFiyatlandirma wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public BigDecimal hesapla() {
        return wrapped.hesapla();
    }
}
