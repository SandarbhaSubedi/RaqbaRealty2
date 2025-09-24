package com.smart.controller;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.smart.entities.User;
import com.smart.entities.Image;
import com.smart.entities.ImageGroup;
import com.smart.services.ImageService;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.smart.dao.UserRepository;
import com.smart.dao.ImageGroupRepository;
import com.smart.dao.ImageRepository;

@Controller
@RequestMapping("/user")
public class ImageController {
    
	@Autowired
    private ImageRepository imageRepository;
	
    private final ImageService imageService;
    
    @Autowired
    private ImageGroupRepository imageGroupRepository;
    
    @Autowired
  	private UserRepository userRepository;

    public ImageController(ImageService imageService,ImageGroupRepository imageGroupRepository ) {
        this.imageService = imageService;
        this.imageGroupRepository = imageGroupRepository;
    }

    
    
    @GetMapping("/upload")
    public String showUploadPage(Model model) {
    	 model.addAttribute("page", "upload");
        return "upload";
    }
    
    

    //method for adding common data to response
  	@ModelAttribute
  	public void addCommonData(Model model, Principal principal) {
  	 
  		
  		//get the user using username(Email)
  		
  		String userName = principal.getName();
  		System.out.println("USERNAME" +userName);
  		
  		User user = userRepository.getUserByUserName(userName);
  		System.out.println("USER" +user);
  		
  		model.addAttribute("user", user);
  		
  	}
   
  

  	@PostMapping("/upload")
  	public ResponseEntity<String> uploadImages(
  		   @RequestParam("imageId") String imageId,
  	        @RequestParam("userId") Integer userId,
  	        @RequestParam("description") String description,
  	        @RequestParam("name") String name,
  	        @RequestParam("title") String title,
  	        @RequestParam("type") String type,
  	        @RequestParam("bedroom") String bedroom,
  	        @RequestParam("currency") String currency,
  	        @RequestParam("location") String location,
  	        @RequestParam("furnishing") String furnishing,
  	        @RequestParam("feature") String feature,
  	        @RequestParam("consultant") String consultant,
  	        @RequestParam("area") String area,
  	        @RequestParam("faculty") String faculty,
  	        @RequestParam("exclusives") String exclusives,
  	        @RequestParam("rerano") String rerano,
  	        @RequestParam("referenceno") String referenceno,
  	        @RequestParam("dldpno") String dldpno,
  	        @RequestParam("files") List<MultipartFile> files)
 {

        // Check if admin exists
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Admin not found with ID: " + userId));

        try {
            imageService.saveImages(user, description, name, title, type, bedroom, currency, location, furnishing, feature, 
                                    consultant, area, faculty, exclusives, rerano, referenceno, dldpno, imageId, files);
            return ResponseEntity.ok("Images uploaded successfully under Image ID: " + imageId);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error saving images: " + e.getMessage());
        }
    }
    

    @GetMapping("/images/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        Optional<Image> imageOptional = imageRepository.findById(id);

        if (imageOptional.isPresent()) {
            Image image = imageOptional.get();
            return ResponseEntity.ok()
                    .contentType(MediaType.valueOf(image.getFileType()))
                    .body(image.getImageData());
        } else {
            return ResponseEntity.notFound().build();
        }
       
    }

    
    @GetMapping("/details/{id}")
    public String showDetails(@PathVariable String id, Model model) {
        ImageGroup imageGroup = imageService.getImageGroupById(id);
        model.addAttribute("imageGroup", imageGroup);
        return "details";
    }
    

    
    
    
  


    
 // Show full details when clicking on an item
    @GetMapping("/gallery/details/{id}")
    public String getImageGroupDetails(@PathVariable String id, Model model) {
        ImageGroup imageGroup = imageService.getImageGroupById(id);
        model.addAttribute("imageGroup", imageGroup);
        return "details";
    }
    
    
    public String getMaxCurrencyByTitle(String title) {
        return imageGroupRepository.findMaxCurrencyByTitle(title);
    }


    @GetMapping("/usergallery")
    public String getUserGallery(
            @RequestParam(defaultValue = "Primary") String title,
            @RequestParam(defaultValue = "All") String bedroom,
            @RequestParam(required = false) String maxCurrency,
            @RequestParam(required = false) Boolean showDetails,
            Model model) {

        // Fetch all available bedrooms for filtering
        List<String> availableBedrooms = imageService.getAvailableBedrooms(title);

        // Get the max currency for the selected title
        String maxAvailableCurrency = imageService.getMaxCurrencyByTitle(title);

        // Fetch filtered ImageGroups
        List<ImageGroup> imageGroups = imageService.getImageGroupsByFilters(title, bedroom, maxCurrency);

        model.addAttribute("page", "usergallery");
        model.addAttribute("title", title);
        model.addAttribute("bedroom", bedroom);
        model.addAttribute("maxCurrency", maxCurrency);
        model.addAttribute("imageGroups", imageGroups);
        model.addAttribute("showDetails", showDetails);
        model.addAttribute("availableBedrooms", availableBedrooms);
        model.addAttribute("maxAvailableCurrency", maxAvailableCurrency);

        return "usergallery";
    }
    
    
    

    
    
    @GetMapping("/delete/{id}")
    public String deleteImageGroup(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        imageService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Image group deleted successfully!");
        return "redirect:/usergallery";  // Redirect to gallery page after deletion
    }


    
    }




    

