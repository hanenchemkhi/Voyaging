package com.perscholas.voyaging.service;

import com.perscholas.voyaging.dto.RoomDTO;
import com.perscholas.voyaging.exception.RoomNotFoundException;

import com.perscholas.voyaging.model.Reservation;
import com.perscholas.voyaging.model.Room;
import com.perscholas.voyaging.repository.ReservationRepository;
import com.perscholas.voyaging.repository.RoomRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Slf4j
public class RoomService {
    final Double TAXE_RATES = 0.07;
    private final RoomRepository roomRepository;

    private final ReservationRepository reservationRepository;


    @Autowired
    public RoomService(RoomRepository roomRepository,
                       ReservationRepository reservationRepository) {
        this.roomRepository = roomRepository;
        this.reservationRepository = reservationRepository;
    }


    public List<RoomDTO> findAvailableRooms(LocalDate checkinDate, LocalDate checkoutDate, int numberRooms, int numberGuests) {
        List<Room> availableRooms = roomRepository.findAll();
        List<Reservation> allReservations = reservationRepository.findAll();
        List<Room> reservedRooms = new ArrayList<>();

        for (Reservation reservation : allReservations) {

            if (checkinDate.isBefore(reservation.getCheckinDate())
                    && checkoutDate.isAfter(reservation.getCheckinDate())) {
                reservedRooms.addAll(reservation.getRooms());
            }
        }
        availableRooms.removeAll(reservedRooms);

        return availableRooms.stream()
                .map(this::convertRoomToRoomDTO)
                .collect(Collectors.toList());

    }

    public void saveRoom(Room room, MultipartFile multipartFile) {

        try {
            byte[] imageArr = multipartFile.getBytes();
            room.setImageName(multipartFile.getOriginalFilename());
            room.setImageData(imageArr);
            roomRepository.save(room);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Room> findAllRooms() {
        return roomRepository.findAll();
    }


    public Room finRoomById(Long id) {
        Optional<Room> room = roomRepository.findById(id);
        return unwrapRoom(room, id);
    }

    static Room unwrapRoom(Optional<Room> room, Long id) {
        if (room.isPresent()) return room.get();
        else throw new RoomNotFoundException(id);
    }

    public void deleteRoom(Long roomId) {

        roomRepository.deleteById(roomId);

    }

    public RoomDTO convertRoomToRoomDTO(Room room) {
        RoomDTO roomDTO = new RoomDTO();
        BeanUtils.copyProperties(room, roomDTO);
        roomDTO.setImageData(Base64.getEncoder().encodeToString(room.getImageData()));
        return roomDTO;
    }

    public ResponseEntity<byte[]> loadImage(Long roomId) {
        Room room = finRoomById(roomId);
        String filename = room.getImageName();
        byte[] imageData = room.getImageData();
        long fileLength = imageData.length;

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=" + filename)
                .contentType(MediaType.valueOf("image/jpeg"))
                .contentLength(fileLength)
                .body(imageData);
    }


    public RoomDTO findRoomDTOById(Long id) {
        return convertRoomToRoomDTO(finRoomById(id));
    }

    public Double calculateTaxes(Long id) {
        return finRoomById(id).getPrice() * TAXE_RATES;
    }

    public Double calculateCostPerRoom(Long id, int nbRooms) {
        return calculateTaxes(id) + finRoomById(id).getPrice();
    }

    public Double calculateTotalCost(Long id, Long lengthOfStay, int nbRooms) {
        return calculateCostPerRoom(id, nbRooms) * lengthOfStay;
    }

}


