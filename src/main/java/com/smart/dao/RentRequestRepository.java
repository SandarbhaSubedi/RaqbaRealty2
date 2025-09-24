package com.smart.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import com.smart.entities.RentRequest;

public interface RentRequestRepository extends JpaRepository<RentRequest, Long> {
}

