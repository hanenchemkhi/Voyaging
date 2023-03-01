package com.perscholas.voyaging.service;

import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class ReservationService {
    public Long findLengthOfStay(LocalDate checkin, LocalDate checkout) {
        return DAYS.between(checkin, checkout);
    }

    public String formatDate(LocalDate date) {
        return date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));

    }

    public String formatPrice(Double price){
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format(price);
    }



}
