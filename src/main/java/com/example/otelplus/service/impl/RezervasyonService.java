package com.example.otelplus.service.impl;

import com.example.otelplus.dto.RezervasyonDto;
import com.example.otelplus.dto.RezervasyonDtoIU;
import com.example.otelplus.model.Oda;
import com.example.otelplus.model.Rezervasyon;
import com.example.otelplus.observer.RezervasyonSubject;
import com.example.otelplus.repository.IOdaRepository;
import com.example.otelplus.repository.IRezervasyonRepository;
import com.example.otelplus.service.IRezervasyonService;
import com.example.otelplus.strategy.FiyatHesaplama;
import com.example.otelplus.strategy.FiyatHesaplayiciContext;
import com.example.otelplus.factory.FiyatStrategyFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class RezervasyonService implements IRezervasyonService {

    private final IRezervasyonRepository rezervasyonRepository;
    private final IOdaRepository odaRepository;
    private final RezervasyonSubject subject;   // ✔ Observer subject

    public RezervasyonService(IRezervasyonRepository rezervasyonRepository,
                              IOdaRepository odaRepository,
                              RezervasyonSubject subject) {
        this.rezervasyonRepository = rezervasyonRepository;
        this.odaRepository = odaRepository;
        this.subject = subject;
    }

    // -------------------------------------------------------------------
    // ✔ create — Yeni rezervasyon oluştur
    // -------------------------------------------------------------------
    @Override
    public RezervasyonDto createRezervasyon(RezervasyonDtoIU dtoIU) {

        Oda oda = odaRepository.findById(dtoIU.getOdaId())
                .orElseThrow(() -> new RuntimeException("Oda bulunamadı."));

        checkOdaUygunluk(dtoIU.getOdaId(),
                dtoIU.getGirisTarihi(),
                dtoIU.getCikisTarihi());

        tarihleriKontrolEt(dtoIU.getGirisTarihi(),
                dtoIU.getCikisTarihi());

        Rezervasyon rezervasyon = new Rezervasyon();
        rezervasyon.setKullaniciId(dtoIU.getKullaniciId());
        rezervasyon.setOda(oda);
        rezervasyon.setGirisTarihi(dtoIU.getGirisTarihi());
        rezervasyon.setCikisTarihi(dtoIU.getCikisTarihi());

        // ✔ Factory + Strategy ile fiyat hesaplama
        BigDecimal fiyat = hesaplaFiyat(oda, dtoIU.getGirisTarihi());
        rezervasyon.setRezervasyonMaliyeti(fiyat);

        Rezervasyon saved = rezervasyonRepository.save(rezervasyon);

        // ✔ Observer’ları tetikle
        subject.notifyObservers(saved);

        return toDto(saved);
    }

    // -------------------------------------------------------------------
    @Override
    public RezervasyonDto getById(Integer id) {
        Rezervasyon r = rezervasyonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rezervasyon bulunamadı."));
        return toDto(r);
    }

    // -------------------------------------------------------------------
    @Override
    public List<RezervasyonDto> getByKullaniciId(Integer kullaniciId) {
        List<Rezervasyon> rezervasyonlar =
                rezervasyonRepository.findByKullaniciId(kullaniciId);
        return rezervasyonlar.stream().map(this::toDto).toList();
    }

    // -------------------------------------------------------------------
    @Override
    public void delete(Integer id) {
        Rezervasyon r = rezervasyonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Silinecek rezervasyon bulunamadı."));
        rezervasyonRepository.delete(r);
    }

    // -------------------------------------------------------------------
    @Override
    public RezervasyonDto update(Integer id, RezervasyonDtoIU dtoIU) {

        Rezervasyon rezervasyon = rezervasyonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rezervasyon bulunamadı."));

        checkOdaUygunluk(dtoIU.getOdaId(),
                dtoIU.getGirisTarihi(),
                dtoIU.getCikisTarihi());

        Oda oda = odaRepository.findById(dtoIU.getOdaId())
                .orElseThrow(() -> new RuntimeException("Oda bulunamadı."));

        rezervasyon.setOda(oda);
        rezervasyon.setGirisTarihi(dtoIU.getGirisTarihi());
        rezervasyon.setCikisTarihi(dtoIU.getCikisTarihi());
        rezervasyon.setKullaniciId(dtoIU.getKullaniciId());

        // ✔ Factory + Strategy ile yeniden hesapla
        BigDecimal fiyat = hesaplaFiyat(oda, dtoIU.getGirisTarihi());
        rezervasyon.setRezervasyonMaliyeti(fiyat);

        Rezervasyon updated = rezervasyonRepository.save(rezervasyon);
        return toDto(updated);
    }

    // ================== YARDIMCI METOTLAR ==================

    private void tarihleriKontrolEt(LocalDate giris, LocalDate cikis) {
        if (cikis.isBefore(giris)) {
            throw new RuntimeException("Çıkış tarihi giriş tarihinden önce olamaz.");
        }
    }

    private void checkOdaUygunluk(Integer odaId,
                                  LocalDate giris,
                                  LocalDate cikis) {

        List<Rezervasyon> cakisanlar =
                rezervasyonRepository.tarihCakisaniBul(odaId, giris, cikis);

        if (!cakisanlar.isEmpty()) {
            throw new RuntimeException("Bu oda verilen tarihlerde dolu.");
        }
    }

    // ✔ Factory kullanan fiyat hesaplama
    private BigDecimal hesaplaFiyat(Oda oda, LocalDate girisTarihi) {

        BigDecimal temelFiyat = oda.getFiyat();

        FiyatHesaplama strategy =
                FiyatStrategyFactory.getStrategy(girisTarihi);

        FiyatHesaplayiciContext context =
                new FiyatHesaplayiciContext(strategy);

        return context.hesapla(temelFiyat);
    }

    // ✔ Entity → DTO
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
