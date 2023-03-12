package com.perscholas.voyaging;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.perscholas.voyaging.model.*;
import com.perscholas.voyaging.repository.AddressRepository;
import com.perscholas.voyaging.repository.CreditCardRepository;
import com.perscholas.voyaging.repository.CustomerRepository;
import com.perscholas.voyaging.repository.RoomTypeRepository;
import com.perscholas.voyaging.service.CustomerService;
import com.perscholas.voyaging.service.RoomService;


import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;


import java.time.LocalDate;

import static com.perscholas.voyaging.model.RoomCategory.*;

@SpringBootTest

class VoyagingApplicationTests {

	@Autowired
	RoomTypeRepository roomTypeRepository;

	@Autowired
	RoomService roomService;

	@Autowired
	CustomerService customerService;
	@Autowired
	CustomerRepository customerRepository;


	@Test
	public void contextLoads() {
	}



	@Test
	public void saveRoomType(){
		RoomType roomType = new RoomType(4,100.0, TWIN_EXECUTIVE_ROOM);

		RoomType newRoomType = roomService.saveRoomType(roomType);
		RoomType retrievedRoomType = roomService.findAllRoomType().get(roomService.findAllRoomType().size()-1);

		assertThat(newRoomType.getRoomCategory()).isEqualTo(roomType.getRoomCategory());
		assertThat(newRoomType.getPrice()).isEqualTo (retrievedRoomType.getPrice());
		assertThat(newRoomType.getMaxGuests()).isEqualTo(roomType.getMaxGuests());

	}
	@Test @Order(1)
	public void updateRoom(){

		RoomType roomType = new RoomType(4,100.0, KING_GUEST_ROOM );
		RoomType newRoomType = roomService.saveRoomType(roomType);

		Room savedRoom = roomService.saveRoom(100, KING_GUEST_ROOM);
		assertThat(savedRoom.getRoomType()).isEqualTo(roomType);
	}


	@Test
	public void saveCustomer(){
		Customer customer = new Customer( );
		customer.setFirstName("Taha");
		customer.setLastName("Taha");
		customer.setEmail("taha@gmail.com");
		customer.setPassword("1234567890");
		customer.setConfirmPassword("1234567890");
		Customer newCustomer = customerRepository.save(customer);

		Customer retrievedCustomer = customerService.findCustomerByEmail("taha@gmail.com");
		assertThat(newCustomer.getEmail()).isEqualTo(retrievedCustomer.getEmail());
		assertThat(newCustomer).isEqualTo(retrievedCustomer);

	}


}
