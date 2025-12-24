package com.example.otelplus.factory;

import com.example.otelplus.strategy.*;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class FiyatStrategyFactory {

    private FiyatStrategyFactory() {
        // new'lenmesin diye private constructor
    }

    public static FiyatHesaplama getStrategy(LocalDate girisTarihi) {

        DayOfWeek gun = girisTarihi.getDayOfWeek();
        int ay = girisTarihi.getMonthValue();

        boolean haftaSonu =
                (gun == DayOfWeek.SATURDAY || gun == DayOfWeek.SUNDAY);
        boolean yazSezonu =
                (ay >= 6 && ay <= 9); // Haziran - Eylül

        if (yazSezonu) {
            return new YazSezonuStrategy();
        } else if (haftaSonu) {
            return new HaftaSonuStrategy();
        } else {
            return new HaftaIciStrategy();
        }
    }
}
