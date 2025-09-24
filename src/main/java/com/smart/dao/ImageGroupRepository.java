package com.smart.dao;



import com.smart.entities.ImageGroup;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ImageGroupRepository extends JpaRepository<ImageGroup, Long> {
    Optional<ImageGroup> findByImageId(String imageId);
    

    // Filter image groups by title
      List<ImageGroup> findByTitle(String title);

      Page<ImageGroup> findAll(Pageable pageable); // Fetch paginated results
      

      @Query("SELECT i FROM ImageGroup i WHERE i.title = :title AND i.bedroom = :bedroom")
      List<ImageGroup> findByTitleAndBedroom(@Param("title") String title, @Param("bedroom") String bedroom);

      @Query("SELECT i FROM ImageGroup i WHERE i.title = :title AND CAST(i.currency AS integer) <= CAST(:maxCurrency AS integer)")
      List<ImageGroup> findByTitleAndMaxCurrency(@Param("title") String title, @Param("maxCurrency") String maxCurrency);

      @Query("SELECT i FROM ImageGroup i WHERE i.title = :title AND i.bedroom = :bedroom AND CAST(i.currency AS integer) <= CAST(:maxCurrency AS integer)")
      List<ImageGroup> findByTitleAndBedroomAndMaxCurrency(@Param("title") String title, @Param("bedroom") String bedroom, @Param("maxCurrency") String maxCurrency);

      @Query("SELECT DISTINCT i.bedroom FROM ImageGroup i WHERE i.title = :title")
      List<String> findDistinctBedroomsByTitle(@Param("title") String title);

      @Query("SELECT MAX(CAST(i.currency AS integer)) FROM ImageGroup i WHERE i.title = :title")
      String findMaxCurrencyByTitle(@Param("title") String title);

      
      void deleteById(Long id);
      
      
      //filtering to get buy or sell page
      @Query("SELECT i FROM ImageGroup i WHERE LOWER(i.type) = LOWER(:type)")
      List<ImageGroup> findByType(@Param("type") String type, Pageable pageable);
      
      int countByType(String type);
      
      
      
      
      
   // Find by Type (Buy/Rent)
      List<ImageGroup> findByType(String type);

      // Get Maximum Available Area
      @Query("SELECT MAX(CAST(ig.area AS integer)) FROM ImageGroup ig WHERE ig.type = :type")
      String findMaxAreaByType(@Param("type") String type);

      // Get Distinct Bedrooms
      @Query("SELECT DISTINCT ig.bedroom FROM ImageGroup ig WHERE ig.type = :type")
      List<String> findDistinctBedroomsByType(@Param("type") String type);
      
     

      // Get Distinct Faculties
      @Query("SELECT DISTINCT ig.faculty FROM ImageGroup ig WHERE ig.type = :type")
      List<String> findDistinctFacultiesByType(@Param("type") String type);
      
      // Get Distinct Faculties
      @Query("SELECT DISTINCT ig.location FROM ImageGroup ig WHERE ig.type = :type")
      List<String> findDistinctLocationsByType(@Param("type") String type);

      @Query("SELECT i FROM ImageGroup i WHERE i.type = :type " +
    	       "AND (:bedroom IS NULL OR :bedroom = '' OR i.bedroom = :bedroom) " +
    	       "AND (:faculty IS NULL OR :faculty = '' OR i.faculty = :faculty) " +
    	       "AND (:maxArea IS NULL OR :maxArea = '' OR CAST(i.area AS integer) <= CAST(:maxArea AS integer))")
    	List<ImageGroup> findFilteredProperties(
    	    @Param("type") String type, 
    	    @Param("bedroom") String bedroom, 
    	    @Param("faculty") String faculty, 
    	    @Param("maxArea") String maxArea
    	);

      @Query("SELECT i FROM ImageGroup i WHERE i.type = :type "
              + "AND (:bedroom IS NULL OR i.bedroom = :bedroom) "
              + "AND (:faculty IS NULL OR i.faculty = :faculty) "
              + "AND (:maxArea IS NULL OR i.area <= :maxArea)")
      Page<ImageGroup> findByTypeAndFilters(
              @Param("type") String type,
              @Param("bedroom") String bedroom,
              @Param("faculty") String faculty,
              @Param("maxArea") String maxArea,
              Pageable pageable);
     
    
      @Query("SELECT ig FROM ImageGroup ig WHERE ig.type = :type")
      Page<ImageGroup> findByType1(@Param("type") String type, Pageable pageable);

      
      //working
      @Query("SELECT i FROM ImageGroup i WHERE i.type = :type "
    		  + "AND (:field IS NULL OR i.field = :field) "
    		  + "AND (:location IS NULL OR i.location = :location) "
              + "AND (:bedroom IS NULL OR i.bedroom = :bedroom) "
              + "AND (:faculty IS NULL OR i.faculty = :faculty) "
              + "AND (:maxArea IS NULL OR i.area <= :maxArea)")
      Page<ImageGroup> findFilteredRentProperties(
              @Param("type") String type,
              @Param("field") String field,
              @Param("location") String location,
              @Param("bedroom") String bedroom,
              @Param("faculty") String faculty,
              @Param("maxArea") Integer maxArea,
              Pageable pageable
      );
      
      @Query("SELECT i FROM ImageGroup i WHERE i.type = :type "
    		  + "AND (:location IS NULL OR i.location = :location) "
    	        + "AND (:bedroom IS NULL OR i.bedroom = :bedroom) "
    	        + "AND (:faculty IS NULL OR i.faculty = :faculty) "
    	        + "AND (:maxArea IS NULL OR i.area <= :maxArea)")
    	Page<ImageGroup> findFilteredBuyProperties(
    	        @Param("type") String type,
    	        @Param("location") String location,
    	        @Param("bedroom") String bedroom,
    	        @Param("faculty") String faculty,
    	        @Param("maxArea") Integer maxArea,
    	        Pageable pageable
    	);

      @Query("SELECT DISTINCT ig.bedroom FROM ImageGroup ig WHERE ig.type = :type ORDER BY ig.bedroom ASC")
      List<String> findDistinctBedroomsByType1(@Param("type") String type);


      
   // Custom query to find properties with exclusives = 'yes'
      List<ImageGroup> findByExclusives(String exclusives);
}
    

