package com.perscholas.voyaging.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record SearchCriteria(@Future(message = "Check-in date can not be in the past")
                             @NotNull(message = "Check-in date can not be empty")
                             LocalDate checkin,
                             @Future (message = "Check-out date can not be in the past")
                             @NotNull(message = "Check-out date can not be empty")
                             LocalDate checkout,
                             @NotNull(message = "Number of rooms can not be empty")
                             @Min(value = 1, message = "Min: 1 room")
                             @Max(value = 4, message = "Max: 4 rooms")
                             int nbRooms,
                             @NotNull(message = "Number of guests can not be empty")
                             @Min(value = 1, message = "Min: 1 guest")
                             @Max(value = 4, message = "Max: 4 total guests/room")
                             int nbGuests) {
}
