package com.perscholas.voyaging.repository;

import com.perscholas.voyaging.model.Address;
import com.perscholas.voyaging.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority,Integer> {


}
