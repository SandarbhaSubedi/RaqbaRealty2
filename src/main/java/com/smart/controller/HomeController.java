package com.smart.controller;

import com.smart.entities.Contact;
import com.smart.entities.Image;
import com.smart.entities.ImageGroup;
import com.smart.dao.ImageGroupRepository;
import com.smart.dao.ImageRepository;
import com.smart.dao.RentRequestRepository;
import com.smart.dao.UserRepository;
import com.smart.entities.Property;
import com.smart.entities.RentRequest;
import com.smart.entities.Review;
import com.smart.entities.User;
import com.smart.services.ImageService;
import com.smart.services.PropertyService;
import com.smart.services.ReviewService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.apache.logging.log4j.message.Message;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	  @Autowired
      private PropertyService propertyService;
	
	  @Autowired
	    private ReviewService reviewService;
	  
	  @Autowired
	    private ImageRepository imageRepository;
	  
	  @Autowired
	    private ImageGroupRepository imageGroupRepository;
	  
	  @Autowired
	    private ImageService imageService;
	  
	  @Autowired
	    private RentRequestRepository rentRequestRepository;
	  
	  
	  // This method is publicly accessible and displays the details of all properties
	  @GetMapping("/properties")
	  public String showProperties(Model model) {
	      // Fetch all properties (you could add pagination here if necessary)
	      Page<Property> propertyPage = propertyService.getAllProperties(0, 10); // Adjust pagination as needed
	      
	      // Convert Page to List
	      List<Property> properties = propertyPage.getContent();
	      
	      
	          
	      

	      model.addAttribute("properties", properties);
	      return "properties"; // The name of the Thymeleaf template (properties.html)
	  }


   

      

        @GetMapping("/properties/{id}")
        public String showPropertyDetails(@PathVariable("id") Integer id, Model model) {
            Property property = propertyService.getPropertyById(id);
            if (property != null) {
                model.addAttribute("property", property);
                return "propertyDetails";
            }
            return "error"; // Show error if property is not found
        }
        
        

    



	
	

	@GetMapping("/about")
	public String about(Model model) {
		model.addAttribute("title","About - Raqba Realty");
		model.addAttribute("page", "about");
		return "about";
	}
	
	@GetMapping("/sell")
	public String sell(Model model) {
		model.addAttribute("title","sell - Raqba Realty");
		model.addAttribute("page", "sell");
		return "sell";
	}
	
	@GetMapping("/")
	public String homereplica(Model model) {
		model.addAttribute("title","home - Raqba Realty");
		model.addAttribute("page", "home");
		// Get exclusive properties from the service layer
        List<ImageGroup> exclusiveProperties = imageService.getExclusiveProperties();
        
        List<Review> reviews = reviewService.getLatestApprovedReviews();
        model.addAttribute("reviews", reviews);
        
        // Add to the model for use in the view
        model.addAttribute("exclusiveProperties", exclusiveProperties);
		return "homereplica";
	}
	
	@GetMapping("/video")
	public String video(Model model) {
		model.addAttribute("title","video - Raqba Realty");
		model.addAttribute("page", "video");
		return "video";
	}
	
	@GetMapping("/photo")
	public String photo(Model model) {
		model.addAttribute("title","photo - Raqba Realty");
		model.addAttribute("page", "photo");
		return "photo";
	}
	
	@GetMapping("/signup")
	public String signup(Model model) {
		
		model.addAttribute("title","Register - Raqba Realty");
		model.addAttribute("user",new User());
		return "signup";
		
	}
	
	// User: Show form
    @GetMapping("/Rentform")
    public String showForm(Model model) {
        model.addAttribute("rentRequest", new RentRequest());
        model.addAttribute("page", "rent");
        return "Rentform";
    }

    // User: Submit form
    @PostMapping("/rent/submit")
    public String submitRequest(@ModelAttribute RentRequest rentRequest, Model model) {
        rentRequestRepository.save(rentRequest);
        model.addAttribute("page", "rent");
        return "redirect:/Rentform?success";
    }

	
	@GetMapping("/login")
	public String customLogin(Model model) {
		
		model.addAttribute("title","Login Page");
		return "login";
	}
	
	
    
	
	
	//Handler for registering user
	@PostMapping("/do_register")
	public String registerUser(@Valid @ModelAttribute("user") User user,BindingResult result1 ,@RequestParam(value="agreement",defaultValue ="false")boolean agreement,Model model,
			HttpSession session) {
		
		try {
		if(!agreement)
		{
			System.out.println("You have not agreed the terms and conditions");
			throw new Exception("You have not agreed the terms and conditions");
		}
		
		//Implementing Validation
		if(result1.hasErrors())
		{
			System.out.println("ERROR"+result1.toString());
			model.addAttribute("user", user);
			return "signup";
		}
		
		user.setRole("ROLE_USER");
		user.setEnabled(true);
		user.setImageUrl("default.png");
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		
		
		System.out.println("Agreement" +agreement);
		System.out.println("USER" +user);
		
		User result =this.userRepository.save(user);
		
		model.addAttribute("message","Successfully Registered");
		
		}catch(Exception e){
			
			e.printStackTrace();
			model.addAttribute("user",user);
			model.addAttribute("errorMessage","Something went wrong! "+ e.getMessage());
			
		}
		
		
		return "signup";
	}
	
	 @GetMapping("/submitReview")
	    public String showReviews(Model model) {
	        List<Review> reviews = reviewService. getAllApprovedReviews();
	        model.addAttribute("reviews", reviews);
	        model.addAttribute("newReview", new Review());
	        model.addAttribute("page", "submitReview");
	        return "submitReview";
	    }

	 
	
	// Submit a review(working)
    @PostMapping("/submitReview")
    public String submitReview(
            @RequestParam String fullName,
            @RequestParam String email,
            @RequestParam String location,
            @RequestParam String reviewMessage,
            @RequestParam Integer starRating) { // Accept star rating

        Review review = new Review();
        review.setFullName(fullName);
        review.setEmail(email);
        review.setLocation(location);
        review.setReviewMessage(reviewMessage);
        review.setStarRating(starRating); // Set star rating
        review.setSubmittedAt(LocalDateTime.now());

        reviewService.saveReview(review);

        return "redirect:/submitReview?success";
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
    

    
    @GetMapping("/gallery")
    public String getGallery(
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

        model.addAttribute("title", title);
        model.addAttribute("bedroom", bedroom);
        model.addAttribute("maxCurrency", maxCurrency);
        model.addAttribute("imageGroups", imageGroups);
        model.addAttribute("showDetails", showDetails);
        model.addAttribute("availableBedrooms", availableBedrooms);
        model.addAttribute("maxAvailableCurrency", maxAvailableCurrency);

        return "gallery";
    }
    
   
    public String showGallery(
        @RequestParam(defaultValue = "0") int page, 
        Model model
    ) {
        int pageSize = 15; 
        Page<ImageGroup> imageGroups = imageService.getPaginatedImageGroups(page, pageSize);

        model.addAttribute("imageGroups", imageGroups.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", imageGroups.getTotalPages() > 0 ? imageGroups.getTotalPages() : 1); // Ensure totalPages is never 0

        return "gallery";
    }

   
    
 // Show full details when clicking on an item
    @GetMapping("/gallery/details/{id}")
    public String getImageGroupDetails(@PathVariable String id, Model model) {
        ImageGroup imageGroup = imageService.getImageGroupById(id);
        model.addAttribute("imageGroup", imageGroup);
        return "details";
    }
    
    
    
    @GetMapping("/buy")
    public String getBuyProperties(
    		@RequestParam(required = false) String field,
   		 @RequestParam(required = false) String location,
           @RequestParam(required = false) String bedroom,
           @RequestParam(required = false) String faculty,
           @RequestParam(required = false) String maxArea,
           @RequestParam(defaultValue = "0") int page, // Default page = 0
           Model model) {
       
       String type = "Buy"; // Ensure we only fetch Rent properties
       int pageSize = 15; // Show 15 properties per page
       Pageable pageable = PageRequest.of(page, pageSize);

       // Fetch only Rent properties that match filters and apply pagination
       Page<ImageGroup> propertyPage = imageGroupRepository.findFilteredRentProperties(
               type,
               (field != null && !field.isEmpty()) ? field : null,
               (location != null && !location.isEmpty()) ? location : null,
               (bedroom != null && !bedroom.isEmpty()) ? bedroom : null,
               (faculty != null && !faculty.isEmpty()) ? faculty : null,
               (maxArea != null && !maxArea.isEmpty()) ? Integer.parseInt(maxArea) : null,
               pageable
       );

       // Get filter options
       List<String> availableLocations = imageService.getAvailableLocations(type);
       List<String> availableBedrooms = imageService.getAvailableBedrooms1(type);
       List<String> availableFaculties = imageService.getAvailableFaculties(type);
       String maxAvailableArea = imageService.getMaxAvailableArea(type);

       // Add attributes to model
       model.addAttribute("page", "buy");
       model.addAttribute("availableLocations", availableLocations);
       model.addAttribute("availableBedrooms", availableBedrooms);
       model.addAttribute("availableFaculties", availableFaculties);
       model.addAttribute("maxArea", maxAvailableArea);
       model.addAttribute("filteredProperties", propertyPage.getContent()); // Get only the filtered Rent properties
       model.addAttribute("currentPage", page);
       model.addAttribute("totalPages", propertyPage.getTotalPages());

       return "buy-properties"; // Return Thymeleaf view
       
   }


    @GetMapping("/rent")
    public String getRentProperties(
    		@RequestParam(required = false) String field,
    		 @RequestParam(required = false) String location,
            @RequestParam(required = false) String bedroom,
            @RequestParam(required = false) String faculty,
            @RequestParam(required = false) String maxArea,
            @RequestParam(defaultValue = "0") int page, // Default page = 0
            Model model) {
        
        String type = "Rent"; // Ensure we only fetch Rent properties
        int pageSize = 15; // Show 15 properties per page
        Pageable pageable = PageRequest.of(page, pageSize);

        // Fetch only Rent properties that match filters and apply pagination
        Page<ImageGroup> propertyPage = imageGroupRepository.findFilteredRentProperties(
                type,
                (field != null && !field.isEmpty()) ? field : null,
                (location != null && !location.isEmpty()) ? location : null,
                (bedroom != null && !bedroom.isEmpty()) ? bedroom : null,
                (faculty != null && !faculty.isEmpty()) ? faculty : null,
                (maxArea != null && !maxArea.isEmpty()) ? Integer.parseInt(maxArea) : null,
                pageable
        );

        // Get filter options
        List<String> availableLocations = imageService.getAvailableLocations(type);
        List<String> availableBedrooms = imageService.getAvailableBedrooms1(type);
        List<String> availableFaculties = imageService.getAvailableFaculties(type);
        String maxAvailableArea = imageService.getMaxAvailableArea(type);

        // Add attributes to model
        model.addAttribute("page", "rent");
        model.addAttribute("availableLocations", availableLocations);
        model.addAttribute("availableBedrooms", availableBedrooms);
        model.addAttribute("availableFaculties", availableFaculties);
        model.addAttribute("maxArea", maxAvailableArea);
        model.addAttribute("filteredProperties", propertyPage.getContent()); // Get only the filtered Rent properties
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", propertyPage.getTotalPages());

        return "rent-properties"; // Return Thymeleaf view
        
    }



    @GetMapping("/offplan")
    public String getOffPlanProperties(
    		@RequestParam(required = false) String field,
   		 @RequestParam(required = false) String location,
           @RequestParam(required = false) String bedroom,
           @RequestParam(required = false) String faculty,
           @RequestParam(required = false) String maxArea,
           @RequestParam(defaultValue = "0") int page, // Default page = 0
           Model model) {
       
       String type = "offplan"; // Ensure we only fetch Rent properties
       int pageSize = 15; // Show 15 properties per page
       Pageable pageable = PageRequest.of(page, pageSize);

       // Fetch only Rent properties that match filters and apply pagination
       Page<ImageGroup> propertyPage = imageGroupRepository.findFilteredRentProperties(
               type,
               (field != null && !field.isEmpty()) ? field : null,
               (location != null && !location.isEmpty()) ? location : null,
               (bedroom != null && !bedroom.isEmpty()) ? bedroom : null,
               (faculty != null && !faculty.isEmpty()) ? faculty : null,
               (maxArea != null && !maxArea.isEmpty()) ? Integer.parseInt(maxArea) : null,
               pageable
       );

       // Get filter options
       List<String> availableLocations = imageService.getAvailableLocations(type);
       List<String> availableBedrooms = imageService.getAvailableBedrooms1(type);
       List<String> availableFaculties = imageService.getAvailableFaculties(type);
       String maxAvailableArea = imageService.getMaxAvailableArea(type);

       // Add attributes to model
       model.addAttribute("page", "offplan");
       model.addAttribute("availableLocations", availableLocations);
       model.addAttribute("availableBedrooms", availableBedrooms);
       model.addAttribute("availableFaculties", availableFaculties);
       model.addAttribute("maxArea", maxAvailableArea);
       model.addAttribute("filteredProperties", propertyPage.getContent()); // Get only the filtered Rent properties
       model.addAttribute("currentPage", page);
       model.addAttribute("totalPages", propertyPage.getTotalPages());

       return "offplan"; // Return Thymeleaf view
       
   }

}
