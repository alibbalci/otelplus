package com.example.otelplus.model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "kullanici")
public class Kullanici implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kullanici_id")
    private Integer kullaniciId;

    @Column(name = "kullanici_adi", nullable = false, unique = true, length = 50)
    private String kullaniciAdi;

    @Column(name = "kullanici_eposta", nullable = false, unique = true, length = 100)
    private String kullaniciEposta;

    @Column(name = "kullanici_sifre", nullable = false, length = 255)
    private String kullaniciSifre;

    @Column(name = "kullanici_cinsiyet", length = 10)
    private String kullaniciCinsiyet;

    @Column(name = "kayit_tarihi", updatable = false)
    private LocalDateTime kayitTarihi;

    // 🔹 ROL İLİŞKİSİ
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rol_id", nullable = false)
    private Rol rol;

    @OneToOne(mappedBy = "kullanici", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private ProfilBilgileri profilBilgileri;

    @OneToOne(mappedBy = "kullanici", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Ayarlar ayarlar;

    @PrePersist
    protected void onCreate() {
        this.kayitTarihi = LocalDateTime.now();
    }

    // ================= UserDetails =================

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Örn: rol_adi = 'ADMIN' → ROLE_ADMIN
        return Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + rol.getRolAdi())
        );
    }

    @Override
    public String getPassword() {
        return this.kullaniciSifre;
    }

    @Override
    public String getUsername() {
        return this.kullaniciAdi;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
