package com.perscholas.voyaging.repository;

import com.perscholas.voyaging.model.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
}
