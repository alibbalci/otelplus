package com.example.otelplus.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor; // Bu eklendi
import lombok.Getter;
import lombok.NoArgsConstructor; // JSON işlemleri için genelde gereklidir
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor // Mapper'daki 'new EtkinlikDto(...)' çağrısı için şart
@NoArgsConstructor  // Hata almamak için güvenlik önlemi
public class EtkinlikDto {

    private Integer etkinlikId;
    private String etkinlikAdi;
    private String etkinlikTuru;
    private LocalDate tarihi;
    private String icerigi;
}