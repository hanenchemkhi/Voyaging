package com.perscholas.voyaging;


import com.perscholas.voyaging.model.*;
import com.perscholas.voyaging.repository.*;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import static com.perscholas.voyaging.model.RoomCategory.*;

@Component
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MyCommandLineRunner implements CommandLineRunner {
    private final RoomTypeRepository roomTypeRepository;
    private final RoomRepository roomRepository;

    private final AuthorityRepository authorityRepository;


    private final UserRepository userRepository;

    @Autowired
    public MyCommandLineRunner(RoomTypeRepository roomTypeRepository, RoomRepository roomRepository, AuthorityRepository authorityRepository,  UserRepository userRepository) {
        this.roomTypeRepository = roomTypeRepository;
        this.roomRepository = roomRepository;
        this.authorityRepository = authorityRepository;
        this.userRepository = userRepository;
    }


    @PostConstruct
    void created(){
        log.warn("=============== My CommandLineRunner Got Created ===============");
    }

    @Async
    @Override
    public void run(String... args) throws Exception {


        RoomType roomType1 = new RoomType(4,120.0,TWIN_GUEST_ROOM);
        RoomType roomType2 = new RoomType(4,140.0,KING_EXECUTIVE_ROOM);



        roomTypeRepository.saveAndFlush(roomType1);
        roomTypeRepository.saveAndFlush(roomType2);



        Room room1 = new Room(125,roomType1);
        Room room2 = new Room(127,roomType1);
        Room room3 = new Room(129,roomType1);
        Room room4 = new Room(131,roomType2);


        roomRepository.saveAndFlush(room1);
        roomRepository.saveAndFlush(room2);
        roomRepository.saveAndFlush(room3);
        roomRepository.saveAndFlush(room4);


        Authority admin = new Authority("ROLE_ADMIN");
        Authority customer = new Authority("ROLE_CUSTOMER");

        authorityRepository.saveAndFlush(admin);
        authorityRepository.saveAndFlush(customer);


        User adminUser = new Admin("Taha","Habib", "habib@gmail.com",new BCryptPasswordEncoder().encode("password1"), "password1", Role.ADMIN);
        adminUser.addAuthority(admin);
        userRepository.save(adminUser);


    }
}