package com.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.entities.Property;
import com.smart.services.PropertyService;

@Controller
public class UserController {

    @Autowired
    private PropertyService propertyService;

    // Display paginated properties
//    @GetMapping("/")
//    public String viewProperties(Model model,
//                                 @RequestParam(defaultValue = "0") int page, 
//                                 @RequestParam(defaultValue = "5") int size) {
//
//        // Create Pageable object based on page and size
//        Pageable pageable = PageRequest.of(page, size);
//        
//        // Get paginated properties from the service
//        Page<Property> propertiesPage = propertyService.getAllProperties(page,size);
//
//        // Add properties and pagination info to the model
//        model.addAttribute("properties", propertiesPage.getContent());
//        model.addAttribute("currentPage", page);
//        model.addAttribute("totalPages", propertiesPage.getTotalPages());
//        model.addAttribute("totalItems", propertiesPage.getTotalElements());
//
//        // Return view for displaying properties
//        return "user/view-properties";
//    }
}
