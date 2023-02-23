package com.perscholas.voyaging.service;

import com.perscholas.voyaging.exception.RoomNotFoundException;
import com.perscholas.voyaging.exception.StorageFileNotFoundException;
import com.perscholas.voyaging.model.Reservation;
import com.perscholas.voyaging.model.Room;
import com.perscholas.voyaging.repository.ReservationRepository;
import com.perscholas.voyaging.repository.RoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


import java.net.MalformedURLException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class RoomService {
    final String UPLOAD_FOLDER = System.getProperty("user.dir") +"/uploads/";
    private final RoomRepository roomRepository;

    private final ReservationRepository reservationRepository;


    @Autowired
    public RoomService(RoomRepository roomRepository,
                       ReservationRepository reservationRepository) {
        this.roomRepository = roomRepository;
        this.reservationRepository = reservationRepository;
    }


    public List<Room> findAvailableRooms(LocalDate checkinDate, LocalDate checkoutDate, int numberRooms, int numberGuests) {
        List<Room> availableRooms = roomRepository.findAll();
        List<Reservation> allReservations = reservationRepository.findAll();
        List<Room> reservedRooms = new ArrayList<>();

        for (Reservation reservation : allReservations) {

            if ( checkinDate.isBefore(reservation.getCheckinDate())
                    && checkoutDate.isAfter(reservation.getCheckinDate())) {
                reservedRooms.addAll(reservation.getRooms());
            }
        }
        availableRooms.removeAll(reservedRooms);

        return availableRooms;

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

        // Create Entity and set profile image.

    }

//    public Path load(String filename) {
//        return rootLocation.resolve(filename);
//    }




    public List<Room> findAllRooms() {
        return roomRepository.findAll();
    }


    public Room finRoomById(Long id){
        Optional<Room> room = roomRepository.findById(id);
        return unwrapPet(room, id);
    }
    static Room unwrapPet(Optional<Room> room, Long id) {
        if (room.isPresent()) return room.get();
        else throw new RoomNotFoundException(id);
    }
}
