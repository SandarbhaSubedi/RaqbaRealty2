package com.smart.services;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.smart.entities.Property;
import com.smart.dao.PropertyRepository;

@Service
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    // Returns a paginated list of properties (use PageRequest for pagination)
    public Page<Property> getAllProperties(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return propertyRepository.findAll(pageable);
    }

    // Method to get properties by user
    public List<Property> getPropertiesByUser(int userId) {
        return propertyRepository.findByUserId(userId);
    }

    public Property getPropertyById(int id) {
        return propertyRepository.findById(id).orElse(null);
    }

    public void addProperty(Property property) {
        // Perform any business logic (e.g., validations) before saving
        propertyRepository.save(property);
    }

    public void uploadPhotos(Integer propertyId, List<MultipartFile> files) throws IOException {
        // Use Optional to handle entity retrieval
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found with ID: " + propertyId));

        for (MultipartFile file : files) {
            property.getPhotos().add(file.getBytes());
        }

        propertyRepository.save(property);
    }

}
