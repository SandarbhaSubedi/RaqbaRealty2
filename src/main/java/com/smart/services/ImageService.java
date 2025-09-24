package com.smart.services;



import com.smart.entities.User;
import com.smart.entities.Image;
import com.smart.entities.ImageGroup;
import com.smart.dao.ImageGroupRepository;
import com.smart.dao.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


@Service
public class ImageService {

    @Autowired
    private ImageGroupRepository imageGroupRepository;

    @Autowired
    private ImageRepository imageRepository;

    public void saveImages(User user, String description, String name, String title, String type, String bedroom, 
            String currency, String location, String furnishing, String feature, String consultant, 
            String area, String faculty, String exclusives, String rerano, String referenceno, 
            String dldpno, String imageId, List<MultipartFile> files) throws IOException{  // Check if an image group with the same imageId already exists
        Optional<ImageGroup> existingGroup = imageGroupRepository.findByImageId(imageId);
        ImageGroup imageGroup;

        if (existingGroup.isPresent()) {
            imageGroup = existingGroup.get(); // Use existing ImageGroup
        } else {
            imageGroup = new ImageGroup();
            imageGroup.setUser(user);
            imageGroup.setImageId(imageId);
            imageGroup.setDescription(description);
            imageGroup.setName(name);
            imageGroup.setTitle(title);
            imageGroup.setType(type);
            imageGroup.setBedroom(bedroom);
            imageGroup.setCurrency(currency);
            imageGroup.setLocation(location);
            imageGroup.setFurnishing(furnishing);
            imageGroup.setFeature(feature);
            imageGroup.setConsultant(consultant);
            imageGroup.setArea(area);
            imageGroup.setFaculty(faculty);
            imageGroup.setExclusives(exclusives);
            imageGroup.setRerano(rerano);
            imageGroup.setReferenceno(referenceno);
            imageGroup.setDldpno(dldpno);
            imageGroup.setImages(new ArrayList<>());
            imageGroup = imageGroupRepository.save(imageGroup); // Save new ImageGroup
        }

        List<Image> imageList = imageGroup.getImages();

        for (MultipartFile file : files) {
            Image image = new Image();
            image.setImageGroup(imageGroup);
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImageData(file.getBytes());
            imageList.add(image);
            imageRepository.save(image);
        }
    }
    public ImageGroup getImageGroupById(String id) {
        return imageGroupRepository.findByImageId(id)
                .orElseThrow(() -> new RuntimeException("Image Group not found with ID: " + id));
    }
    
    public List<ImageGroup> getAllImageGroups() {
        return imageGroupRepository.findAll();  // Return all image groups
    }
 
   

        
    // Fetch all ImageGroups by title only
    public List<ImageGroup> getImageGroupsByTitle(String title) {
        return imageGroupRepository.findByTitle(title);
    }

    
    public List<ImageGroup> getImageGroupsByFilters(String title, String bedroom, String maxCurrency) {
        if (bedroom.equalsIgnoreCase("All") && (maxCurrency == null || maxCurrency.isEmpty())) {
            return imageGroupRepository.findByTitle(title);
        } else if (bedroom.equalsIgnoreCase("All")) {
            return imageGroupRepository.findByTitleAndMaxCurrency(title, maxCurrency);
        } else if (maxCurrency == null || maxCurrency.isEmpty()) {
            return imageGroupRepository.findByTitleAndBedroom(title, bedroom);
        } else {
            return imageGroupRepository.findByTitleAndBedroomAndMaxCurrency(title, bedroom, maxCurrency);
        }
    }
    
    //Implemening showing 15 pages after filtering
    public Page<ImageGroup> getPaginatedImageGroups(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return imageGroupRepository.findAll(pageable);
    }


    public List<String> getAvailableBedroomsByTitle(String title) {
        return imageGroupRepository.findDistinctBedroomsByTitle(title);
    }
    
    public List<String> getAvailableBedrooms(String title) {
        return imageGroupRepository.findDistinctBedroomsByTitle(title);
    }

    
    
    public String getMaxCurrencyByTitle(String title) {
        return imageGroupRepository.findMaxCurrencyByTitle(title);
    }
    
    //implementing user to delete
    public void deleteById(Long id) {
        imageGroupRepository.deleteById(id);
    }
    
    

    // for getting buy and rent page
    public List<ImageGroup> getImagesByType(String type, int page) {
        int pageSize = 20;
        Pageable pageable = PageRequest.of(page, pageSize);
        return imageGroupRepository.findByType(type, pageable);
    }

    public int getTotalPages(String type) {
        int totalResults = imageGroupRepository.countByType(type);
        return (int) Math.ceil(totalResults / 20.0);  // Calculate total pages based on page size
    }
    
    
   
    public List<String> getAvailableBedroom(String type) {
        return imageGroupRepository.findDistinctBedroomsByType(type);
    }

    public List<String> getAvailableFaculties(String type) {
        return imageGroupRepository.findDistinctFacultiesByType(type);
    }
    
    public List<String> getAvailableLocations(String type) {
        return imageGroupRepository.findDistinctLocationsByType(type);
    }

    public String getMaxAvailableArea(String type) {
        return imageGroupRepository.findMaxAreaByType(type);
    }

    public List<ImageGroup> getFilteredProperties(String type, String bedroom, String faculty, String maxArea) {
        return imageGroupRepository.findFilteredProperties(type, bedroom, faculty, maxArea);
    }
    
    
 // Method to fetch exclusive properties where exclusives = 'yes'
    public List<ImageGroup> getExclusiveProperties() {
        return imageGroupRepository.findByExclusives("yes");
    }
    
    //implementing pagination in buy/rent/offplan
    public Page<ImageGroup> getFilteredPropertiesPaginated(String type, String bedroom, String faculty, String maxArea, Pageable pageable) {
        return imageGroupRepository.findByTypeAndFilters(type, bedroom, faculty, maxArea, pageable);
    }
   
    public List<String> getAvailableBedrooms1(String type) {
        return imageGroupRepository.findDistinctBedroomsByType1(type);
    }
    }







