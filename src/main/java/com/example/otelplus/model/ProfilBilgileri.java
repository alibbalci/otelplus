package com.example.otelplus.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "profil_bilgileri")
public class ProfilBilgileri {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profil_id")
    private Integer profilId;

    @Column(name = "isim", length = 100)
    private String isim;

    @Column(name = "soyisim", length = 100)
    private String soyisim;

    @Column(name = "dogum_tarihi")
    private LocalDate dogumTarihi;

    @Column(name = "ulke", length = 50)
    private String ulke;

    @Column(name = "resim", length = 255)
    private String resim;

    @Column(name = "kullanici_biyografi")
    private String kullaniciBiyografi;
    
    // ----- İLİŞKİ SAHİBİ (Adım 6) -----
    // Bu, "profil_bilgileri" tablosundaki "kullanici_id" sütununu yönetir.
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kullanici_id", referencedColumnName = "kullanici_id", nullable = false, unique = true)
    private Kullanici kullanici;
}