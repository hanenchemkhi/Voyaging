package com.perscholas.voyaging.service;

import com.perscholas.voyaging.controller.ImageController;

import com.perscholas.voyaging.exception.RoomNotFoundException;

import com.perscholas.voyaging.model.*;
import com.perscholas.voyaging.repository.*;
import lombok.extern.slf4j.Slf4j;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;

import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import java.util.*;
import java.util.function.Function;

import java.util.stream.Collectors;

import static com.fasterxml.jackson.databind.type.LogicalType.DateTime;

@Service
@Slf4j
public class RoomService {
    private final RoomImageRepository roomImageRepository;

    private final RoomTypeRepository roomTypeRepository;

    private final RoomRepository roomRepository;

    private final ReservationRepository reservationRepository;
    private final Path ROOT_FOLDER = Paths.get("./uploads");
    final Double ROOM_TAXE_RATES = 0.07;


    @Autowired
    public RoomService(RoomRepository roomRepository,
                       ReservationRepository reservationRepository,
                       RoomTypeRepository roomTypeRepository,
                       RoomImageRepository roomImageRepository) {
        this.roomRepository = roomRepository;
        this.reservationRepository = reservationRepository;
        this.roomTypeRepository = roomTypeRepository;
        this.roomImageRepository = roomImageRepository;
    }





    public RoomType saveRoomType(RoomType roomType){

        return roomTypeRepository.save(roomType);

    }



    public RoomImage saveImage(MultipartFile file,Long roomTypeId) throws Exception{
        log.warn(roomTypeId.toString());

        try {
            String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            RoomType roomType = roomTypeRepository.findById(roomTypeId).get();

            String roomCategory = roomType.getRoomCategory().getCategory();
            String imageName = roomCategory.concat("-")
                    .concat(new SimpleDateFormat("yyyyMMddHHmmss'.txt'").format(new Date()))
                    .concat(ext);

            Files.copy(file.getInputStream(), this.ROOT_FOLDER.resolve(imageName));

            Path path = ROOT_FOLDER.resolve(imageName);

            String url = MvcUriComponentsBuilder
                    .fromMethodName(ImageController.class, "getImage", path.getFileName().toString()).build().toString();


            RoomImage roomImage = new RoomImage();
            log.warn("before if findRoomImage by roomTypeId");

            if(roomImageRepository.existsByRoomTypeId(roomTypeId)){
                log.warn("inside if findRoomImage by roomTypeId");
                roomImage = roomImageRepository.findByRoomTypeId(roomTypeId);

            }
            log.warn("outside if findRoomImage by roomTypeId");
            roomImage.setImageUrl(url);
            roomImage.setImageName(imageName);
            log.warn(imageName);
            roomImage.setRoomType(roomType);
            log.warn(roomCategory);
            return roomImageRepository.save(roomImage);


        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new Exception("A file of that name already exists.");
            } else {
                throw new Exception("Error copying file to HD " + file.getOriginalFilename());
            }

        }

    }
    public void init() throws Exception {
        try {
            if(Files.exists(ROOT_FOLDER)){
                log.debug("Folder Exists!");
            }else {
                Files.createDirectories(ROOT_FOLDER);
                log.debug("Folder Created!");
            }
        } catch (IOException e) {
            throw new Exception("Could not initialize folder for upload!");
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



    public void saveRoomCategory(RoomType roomType, MultipartFile file) {

        RoomCategory roomCategory = roomType.getRoomCategory();

        if(roomTypeRepository.existsRoomTypesByRoomCategory(roomCategory)){
            //update roomType
            RoomType roomTypeToUpdate = roomTypeRepository.findByRoomCategory(roomCategory);
            roomTypeToUpdate.setMaxGuests(roomType.getMaxGuests());
            roomTypeToUpdate.setPrice(roomType.getPrice());
            roomTypeToUpdate.setAmenities(roomType.getAmenities());


            try {
                roomTypeToUpdate.setRoomImage(saveImage(file, roomTypeToUpdate.getId()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            roomTypeRepository.save(roomTypeToUpdate);



        }else {
            roomTypeRepository.save(roomType);
            try {
                saveImage(file, roomType.getId());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }


    public Double calculateTaxes(Long id) {
        return finRoomById(id).getRoomType().getPrice() * ROOM_TAXE_RATES;
    }

    public Double calculateCostPerRoom(Long id, int nbRooms) {
        return (calculateTaxes(id) + finRoomById(id).getRoomType().getPrice())*nbRooms;
    }

    public Double calculateTotalCost(Long id, Long lengthOfStay, int nbRooms) {
        return calculateCostPerRoom(id, nbRooms) * lengthOfStay;
    }

    public Resource load(String filename) {
        try {
            Path file = ROOT_FOLDER.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }


    public Room saveRoom(Integer roomNumber, RoomCategory roomCategory) {

        Room room = new Room();
        if (roomRepository.existsRoomByRoomNumber(roomNumber)) {
            room = roomRepository.findRoomByRoomNumber(roomNumber);
        }
            room.setRoomNumber(roomNumber);
            RoomType roomType =  roomTypeRepository.findByRoomCategory(roomCategory);
            room.setRoomType(roomType);
            return roomRepository.save(room);

    }

    public RoomType findRoomTypeById(Long roomTypeId) {
        return roomTypeRepository.findById(roomTypeId).get();
    }


    public void deleteRoomType(Long roomTypeId) {

        roomTypeRepository.deleteById(roomTypeId);
    }


    public Set<RoomCategory> findRoomCategories() {

        return roomTypeRepository.findAll()
                .stream().
                map(roomType -> roomType.getRoomCategory())
                .collect(Collectors.toSet());
    }

    public List<RoomType> findAllRoomType() {
        return roomTypeRepository.findAll();
    }

    public List<Room> findAvailableRooms(LocalDate checkinDate, LocalDate checkoutDate) {

        List<Room> availableRooms = roomRepository.findAll();
        List<Reservation> allReservations = reservationRepository.findAll();

        List<Room> reservedRooms = new ArrayList<>();

        for (Reservation reservation : allReservations) {

            if ((checkinDate.isBefore(reservation.getCheckinDate())) || (checkinDate.isEqual(reservation.getCheckinDate()))
                    && (checkoutDate.isAfter(reservation.getCheckoutDate()))||(checkoutDate.isEqual(reservation.getCheckoutDate()))) {
                reservedRooms.addAll(reservation.getRooms());
            }
        }

        availableRooms.removeAll(reservedRooms);

        return availableRooms;

    }

    public Set<RoomType> availableRoomType(LocalDate checkinDate, LocalDate checkoutDate,
                                           int numberRooms,int numberGuests) {

        List<Room> availableRooms = findAvailableRooms(checkinDate, checkoutDate);

        return availableRooms.stream()
                .map(room -> room.getRoomType())
                .filter(roomType -> roomType.getMaxGuests()>=numberGuests)
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()))
                // Convert this map into a stream
                .entrySet()
                .stream()
                // Check if frequency > numberRooms
                // for duplicate elements
                .filter(m -> m.getValue() >= numberRooms)
                // Find such elements
                .map(Map.Entry::getKey)
                // And Collect them in a Set
                .collect(Collectors.toSet());

    }
}


