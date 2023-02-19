package com.perscholas.voyaging.repository;

import com.perscholas.voyaging.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {
}
