package com.example.otelplus.service.impl;

import com.example.otelplus.dto.OdaDto;
import com.example.otelplus.dto.OtelDetayDto;
import com.example.otelplus.dto.OtelDto;
import com.example.otelplus.dto.OtelYorumDto;
import com.example.otelplus.model.Otel;
import com.example.otelplus.model.Sehir;
import com.example.otelplus.repository.IOtelRepository;
import com.example.otelplus.repository.SehirRepository;
import com.example.otelplus.service.IOtelService;
import org.springframework.stereotype.Service;
import com.example.otelplus.model.Ozellik;

import java.util.List;

@Service
public class OtelService implements IOtelService {

        private final IOtelRepository otelRepository;
        private final SehirRepository sehirRepository;

        public OtelService(IOtelRepository otelRepository,
                        SehirRepository sehirRepository) {
                this.otelRepository = otelRepository;
                this.sehirRepository = sehirRepository;
        }

        // ---------------------------------------------------------
        // 1) Listeleme için OtelDto
        // ---------------------------------------------------------
        @Override
        public List<OtelDto> getAllOtels() {
                return otelRepository.findAll()
                                .stream()
                                .map(this::toOtelDto)
                                .toList();
        }

        @Override
        public OtelDetayDto getOtelById(Integer id) {
                Otel otel = otelRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Otel bulunamadı: " + id));
                return toOtelDetayDto(otel);
        }

        private OtelDto toOtelDto(Otel otel) {
                OtelDto dto = new OtelDto();
                dto.setOtelId(otel.getOtelId());
                dto.setOtelAdi(otel.getOtelAdi());
                dto.setSehir(otel.getSehir().getAdi()); // ✅ Sehir → String
                dto.setOrtalamaPuan(hesaplaOrtalamaPuan(otel));
                return dto;
        }

        private OtelDetayDto toOtelDetayDto(Otel otel) {
                OtelDetayDto dto = new OtelDetayDto();

                dto.setOtelId(otel.getOtelId());
                dto.setOtelAdi(otel.getOtelAdi());
                dto.setAdres(otel.getAdres());
                dto.setSehir(otel.getSehir().getAdi()); // ✅ Sehir → String
                dto.setEnlem(otel.getEnlem());
                dto.setBoylam(otel.getBoylam());

                dto.setOzellikler(
                                otel.getOzellikler()
                                                .stream()
                                                .map(oz -> oz.getOzellik().getOzellikAdi())
                                                .toList());

                dto.setOdalar(
                                otel.getOdalar()
                                                .stream()
                                                .map(oda -> new OdaDto(
                                                                oda.getOdaId(),
                                                                oda.getOdaTipi(),
                                                                oda.getKapasite(),
                                                                oda.getFiyat()))
                                                .toList());

                dto.setYorumlar(
                                otel.getYorumlar()
                                                .stream()
                                                .map(y -> new OtelYorumDto(
                                                                y.getYorumId(),
                                                                y.getKullanici().getKullaniciAdi(),
                                                                y.getYorumIcerigi(),
                                                                y.getPuan(),
                                                                y.getYorumTarihi()))
                                                .toList());

                dto.setOrtalamaPuan(hesaplaOrtalamaPuan(otel));
                return dto;
        }

        // ---------------------------------------------------------
        // Ortalama puan hesaplayan yardımcı metot
        // ---------------------------------------------------------
        private Double hesaplaOrtalamaPuan(Otel otel) {
                if (otel.getYorumlar() == null || otel.getYorumlar().isEmpty()) {
                        return 0.0;
                }

                return otel.getYorumlar()
                                .stream()
                                .mapToDouble(y -> y.getPuan())
                                .average()
                                .orElse(0.0);
        }

        // ---------------------------------------------------------
        // Şehir arama fonksiyonu
        // ---------------------------------------------------------
        @Override
        public List<OtelDto> searchBySehirFn(String sehir, String sort) {
                return otelRepository.searchBySehirFn(sehir, sort)
                                .stream()
                                .map(p -> {
                                        OtelDto dto = new OtelDto();
                                        dto.setOtelId(p.getOtel_id());
                                        dto.setOtelAdi(p.getOtel_adi());
                                        dto.setSehir(p.getSehir());
                                        dto.setOrtalamaPuan(
                                                        p.getOrt_puan() != null ? p.getOrt_puan().doubleValue() : 0.0);
                                        dto.setMinFiyat(
                                                        p.getMin_fiyat() != null ? p.getMin_fiyat().doubleValue()
                                                                        : 0.0);
                                        return dto;
                                })
                                .toList();
        }

        @Override
        public void deleteById(Integer id) {
                otelRepository.deleteById(id);
        }

        // ---------------------------------------------------------
        // 🏨 Admin → Otel ekleme
        // ---------------------------------------------------------
        @Override
        public OtelDto otelEkle(OtelDto dto) {

                Sehir sehir = sehirRepository.findByAdiIgnoreCase(dto.getSehir())
                                .orElseThrow(() -> new RuntimeException("Şehir bulunamadı: " + dto.getSehir()));

                Otel otel = new Otel();
                otel.setOtelAdi(dto.getOtelAdi());
                otel.setAdres(dto.getAdres());
                otel.setTelefon(dto.getTelefon());
                otel.setSehir(sehir);

                Otel saved = otelRepository.save(otel);

                OtelDto result = new OtelDto();
                result.setOtelId(saved.getOtelId());
                result.setOtelAdi(saved.getOtelAdi());
                result.setSehir(saved.getSehir().getAdi());
                result.setOrtalamaPuan(0.0);
                result.setMinFiyat(0.0);

                return result;
        }
}
