package com.smart.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smart.entities.Property;

public interface PropertyRepository extends JpaRepository<Property, Integer> {

    // Pagination query for finding properties by user ID
    @Query("from Property p where p.user.id = :userId")
    public Page<Property> findPropertiesByUser(@Param("userId") int userId, Pageable pageable);


    @Query("SELECT p FROM Property p WHERE " +
            "(:location IS NULL OR LOWER(p.location) LIKE LOWER(CONCAT('%', :location, '%'))) AND " +
            "(:type IS NULL OR LOWER(p.type) = LOWER(:type)) AND " +
            "(:area IS NULL OR p.area >= :area)")
     List<Property> filterProperties(@Param("location") String location,
                                      @Param("type") String type,
                                      @Param("area") String area);
    
    List<Property> findByUserId(int userId);
}

