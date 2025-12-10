package com.example.otelplus.service.impl;

import com.example.otelplus.dto.RezervasyonDto;
import com.example.otelplus.dto.RezervasyonDtoIU;
import com.example.otelplus.model.Oda;
import com.example.otelplus.model.Rezervasyon;
import com.example.otelplus.repository.IOdaRepository;
import com.example.otelplus.repository.IRezervasyonRepository;
import com.example.otelplus.service.IRezervasyonService;
import com.example.otelplus.strategy.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RezervasyonService implements IRezervasyonService {

    private final IRezervasyonRepository rezervasyonRepository;
    private final IOdaRepository odaRepository;

    public RezervasyonService(IRezervasyonRepository rezervasyonRepository,
                              IOdaRepository odaRepository) {
        this.rezervasyonRepository = rezervasyonRepository;
        this.odaRepository = odaRepository;
    }

    @Override
    public RezervasyonDto createRezervasyon(RezervasyonDtoIU dtoIU) {

        Oda oda = odaRepository.findById(dtoIU.getOdaId())
                .orElseThrow(() -> new RuntimeException("Oda bulunamadı."));

        checkOdaUygunluk(dtoIU.getOdaId(), dtoIU.getGirisTarihi(), dtoIU.getCikisTarihi());
        tarihleriKontrolEt(dtoIU.getGirisTarihi(), dtoIU.getCikisTarihi());

        Rezervasyon rezervasyon = new Rezervasyon();
        rezervasyon.setKullaniciId(dtoIU.getKullaniciId());
        rezervasyon.setOda(oda);
        rezervasyon.setGirisTarihi(dtoIU.getGirisTarihi());
        rezervasyon.setCikisTarihi(dtoIU.getCikisTarihi());

        double fiyat = hesaplaFiyat(oda);
        rezervasyon.setRezervasyonMaliyeti(fiyat);

        Rezervasyon saved = rezervasyonRepository.save(rezervasyon);

        return toDto(saved);
    }


    // -------------------------------------------------------------------
    // ✔ getById — Tek rezervasyon getir
    // -------------------------------------------------------------------
    @Override
    public RezervasyonDto getById(Integer id) {
        Rezervasyon r = rezervasyonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rezervasyon bulunamadı."));

        return toDto(r);
    }


    // -------------------------------------------------------------------
    // ✔ getByKullaniciId — Bir kullanıcının tüm rezervasyonları
    // -------------------------------------------------------------------
    @Override
    public List<RezervasyonDto> getByKullaniciId(Integer kullaniciId) {

        List<Rezervasyon> rezervasyonlar =
                rezervasyonRepository.findByKullaniciId(kullaniciId);


        return rezervasyonlar.stream().map(this::toDto).toList();
    }


    private void tarihleriKontrolEt(LocalDate giris, LocalDate cikis) {
        if (cikis.isBefore(giris)) {
            throw new RuntimeException("Çıkış tarihi giriş tarihinden önce olamaz.");
        }
    }


    // -------------------------------------------------------------------
    // ✔ delete — Rezervasyon iptali
    // -------------------------------------------------------------------
    @Override
    public void delete(Integer id) {
        Rezervasyon r = rezervasyonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Silinecek rezervasyon bulunamadı."));
        rezervasyonRepository.delete(r);
    }


    // -------------------------------------------------------------------
    // ✔ update — Tarih güncelleme + yeniden uygunluk kontrolü
    // -------------------------------------------------------------------
    @Override
    public RezervasyonDto update(Integer id, RezervasyonDtoIU dtoIU) {

        Rezervasyon rezervasyon = rezervasyonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rezervasyon bulunamadı."));

        // Tarih değişmişse uygunluk kontrolü yap
        checkOdaUygunluk(dtoIU.getOdaId(), dtoIU.getGirisTarihi(), dtoIU.getCikisTarihi());

        Oda oda = odaRepository.findById(dtoIU.getOdaId())
                .orElseThrow(() -> new RuntimeException("Oda bulunamadı."));

        rezervasyon.setOda(oda);
        rezervasyon.setGirisTarihi(dtoIU.getGirisTarihi());
        rezervasyon.setCikisTarihi(dtoIU.getCikisTarihi());
        rezervasyon.setKullaniciId(dtoIU.getKullaniciId());

        // fiyat yeniden hesaplanır
        rezervasyon.setRezervasyonMaliyeti(hesaplaFiyat(oda));

        Rezervasyon updated = rezervasyonRepository.save(rezervasyon);

        return toDto(updated);
    }


    // ---------------- YARDIMCI METOTLAR ----------------

    private void checkOdaUygunluk(Integer odaId, LocalDate giris, LocalDate cikis) {

        List<Rezervasyon> cakisanlar =
                rezervasyonRepository.tarihCakisaniBul(odaId, giris, cikis);

        if (!cakisanlar.isEmpty()) {
            throw new RuntimeException("Bu oda verilen tarihlerde dolu.");
        }
    }

    private FiyatHesaplama stratejiSec(Oda oda) {
        return switch (oda.getOdaTipi().toLowerCase()) {
            case "standart" -> new StandartOda();
            case "deluxe"   -> new DeluxeOda();
            case "suite"    -> new SuitOda();
            default         -> new DefaultOda();
        };
    }

    private double hesaplaFiyat(Oda oda) {
        FiyatHesaplama strategy = stratejiSec(oda);
        FiyatHesaplayiciContext context = new FiyatHesaplayiciContext(strategy);
        return context.hesapla(oda);
    }





    private RezervasyonDto toDto(Rezervasyon r) {
        RezervasyonDto dto = new RezervasyonDto();
        dto.setRezervasyonId(r.getRezervasyonId());
        dto.setOtelAdi(r.getOda().getOtel().getOtelAdi());
        dto.setOdaTipi(r.getOda().getOdaTipi());
        dto.setRezervasyonMaliyeti(r.getRezervasyonMaliyeti());
        dto.setGirisTarihi(r.getGirisTarihi());
        dto.setCikisTarihi(r.getCikisTarihi());
        return dto;
    }
}
