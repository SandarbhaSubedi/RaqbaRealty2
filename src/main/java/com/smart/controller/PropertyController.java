package com.smart.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smart.dao.PropertyRepository;
import com.smart.entities.Property;
import com.smart.helper.Message;

import jakarta.servlet.http.HttpSession;

@Controller

public class PropertyController {

    


    @Autowired
    private PropertyRepository propertyRepository;

   
    @GetMapping("/properties/filter")
    public String filterProperties(@RequestParam(required = false) String location,
                                   @RequestParam(required = false) String type,
                                   @RequestParam(required = false) String area,
                                   Model model) {
        List<Property> filteredProperties = propertyRepository.filterProperties(location, type, area);
        model.addAttribute("properties", filteredProperties);
        return "properties";
    }

    


}
