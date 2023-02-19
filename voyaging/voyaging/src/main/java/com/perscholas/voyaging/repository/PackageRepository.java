package com.perscholas.voyaging.repository;

import com.perscholas.voyaging.model.Package;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface PackageRepository extends JpaRepository<Package,Long> {
}
