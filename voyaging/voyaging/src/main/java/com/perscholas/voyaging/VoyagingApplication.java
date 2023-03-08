package com.perscholas.voyaging;

import com.perscholas.voyaging.service.RoomService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class VoyagingApplication {

	public static void main(String[] args) {
		SpringApplication.run(VoyagingApplication.class, args);
	}



}
