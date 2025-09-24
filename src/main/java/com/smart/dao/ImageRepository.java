package com.smart.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import com.smart.entities.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
