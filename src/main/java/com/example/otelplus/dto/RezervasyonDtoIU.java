package com.example.otelplus.dto;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RezervasyonDtoIU {

    private Integer odaId;
    private Integer kullaniciId;
    private LocalDate girisTarihi;
    private LocalDate cikisTarihi;

    // RezervasyonDtoIU sınıfının içine, en alta bu kodu yapıştır:
    public Integer getOdaId() {
        return this.odaId;
    }
}
