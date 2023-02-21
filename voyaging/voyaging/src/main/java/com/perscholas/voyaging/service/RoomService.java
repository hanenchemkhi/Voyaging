package com.perscholas.voyaging.service;

import com.perscholas.voyaging.exception.StorageFileNotFoundException;
import com.perscholas.voyaging.model.Reservation;
import com.perscholas.voyaging.model.Room;
import com.perscholas.voyaging.repository.ReservationRepository;
import com.perscholas.voyaging.repository.RoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;



@Service
@Slf4j
public class RoomService {
    final String UPLOAD_FOLDER = "uploads/";

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

    public void saveRoom(Room room, MultipartFile image) {

        if (!Files.exists(Paths.get(UPLOAD_FOLDER))) {
            try {
                Files.createDirectories(Paths.get(UPLOAD_FOLDER));

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        Path fileNameAndPath = Paths.get(UPLOAD_FOLDER, image.getOriginalFilename());
        try {
            Files.write(fileNameAndPath, image.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        room.setImageUrl(fileNameAndPath.toString());

        roomRepository.save(room);
    }

    public Resource loadAsResource(String imageName) {
        Path rootLocation = Paths.get(UPLOAD_FOLDER);
        try {
            Path file = rootLocation.resolve(imageName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + imageName);

            }
        }
        catch (Exception e) {
            throw new StorageFileNotFoundException("Could not read file: " + imageName);
        }
    }


    public List<Room> findAllRooms() {
        return roomRepository.findAll();
    }

    public List<String> listAllPhotos() {
        List<Room> allRooms = roomRepository.findAll();
        return allRooms.stream().map(Room::getImageUrl).collect(Collectors.toList());
    }


}
