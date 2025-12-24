package com.example.otelplus.observer;

import com.example.otelplus.model.Kullanici;
import com.example.otelplus.model.Rezervasyon;
import com.example.otelplus.repository.KullaniciRepository;
import org.springframework.stereotype.Component;

@Component
public class EmailObserver implements RezervasyonObserver{

    private final KullaniciRepository kullaniciRepository;

    public EmailObserver(KullaniciRepository kullaniciRepository) {
        this.kullaniciRepository = kullaniciRepository;
    }

    @Override
    public void onRezervasyonCreated(Rezervasyon rezervasyon) {
        Kullanici k = kullaniciRepository.findById(rezervasyon.getKullaniciId())
                .orElse(null);

        String email = (k != null)
                ? k.getKullaniciEposta()
                : "bilinmeyen@otelplus.com";

        System.out.println("📧 MAIL GÖNDERİLDİ");
        System.out.println("Kime: " + email);
        System.out.println("Mesaj: Rezervasyonunuz başarıyla oluşturuldu. ID = "
                + rezervasyon.getRezervasyonId());
        System.out.println("-----------------------------------");
    }
}
