package com.smart.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.smart.dao.ContactRepository;
import com.smart.dao.PropertyRepository;
import com.smart.dao.RentRequestRepository;
import com.smart.dao.UserRepository;
import com.smart.entities.Property;
import com.smart.entities.RentRequest;
import com.smart.entities.Review;
import com.smart.entities.Contact;
import com.smart.entities.User;
import com.smart.services.ContactService;
import com.smart.services.PropertyService;
import com.smart.services.ReviewService;

@Controller
@RequestMapping("/user")
public class AdminController {

    @Autowired
    private PropertyService propertyService;
    
    @Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PropertyRepository propertyrepository;
	
	@Autowired
    private ContactService contactService;
	
	@Autowired
    private ContactRepository contactRepository;

	
	@Autowired
    private RentRequestRepository rentRequestRepository;
	
	 @Autowired
	    private ReviewService reviewService;

	  
	  
	 

	    
	    // Admin: View all requests
	    @GetMapping("/admin-rent")
	    public String viewRequests(Model model) {
	        model.addAttribute("requests", rentRequestRepository.findAll());
	        model.addAttribute("page", "rent");
	        return "user/admin-rent";
	    }

	    // Admin: Mark as completed
	    @PostMapping("/rent/complete/{id}")
	    public String markCompleted(@PathVariable Long id) {
	        rentRequestRepository.findById(id).ifPresent(request -> {
	            request.setCompleted(true);
	            rentRequestRepository.save(request);
	        });
	        return "redirect:/user/admin-rent";
	    }

	    // Admin: Delete request
	    @PostMapping("/rent/delete/{id}")
	    public String deleteRequest(@PathVariable Long id) {
	        rentRequestRepository.deleteById(id);
	        return "redirect:/user/admin-rent";
	    }
	  
	  
	 // Method to display the list of all contacts
    @GetMapping("/viewContacts")
    public String showContacts(Model model) {
        // Fetch a paginated list of contacts
        List<Contact> contacts = contactService.getAllContacts(); // Adjust pagination as needed

     

        // Add contacts to the model
        model.addAttribute("contacts", contacts);
        model.addAttribute("page", "contacts");
        return "viewContacts"; // Thymeleaf template name (viewContacts.html)
    }
    
    // Handle updating the contact's status
    @PostMapping("/updateContactStatus")
    public String updateContactStatus(@RequestParam("id") Long id, @RequestParam("status") String status, RedirectAttributes redirectAttributes) {
        try {
            // Update the contact status in the database
            contactService.updateContactStatus(id, status);
            redirectAttributes.addFlashAttribute("message", "Status updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to update status: " + e.getMessage());
        }
        return "redirect:/user/viewContacts";
    }


    @DeleteMapping("/deleteContact")
    @ResponseBody
    public String deleteContact(@RequestParam Long contactId) {
        try {
            contactService.deleteContact(contactId);
            return "Contact deleted successfully!";
        } catch (Exception e) {
            return "Error deleting contact: " + e.getMessage();
        }
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
  	
  	
    // Admin panel view
    @GetMapping("/admindashboard")
    public String admindashboard(Model model) {
    	model.addAttribute("page", "admindashboard");
        return "/user/admindashboard";
    }
  	
    
   
    
        //Making admin able to view properties
    @GetMapping("/userProperties")
	  public String showProperties(Model model) {
	      // Fetch all properties (you could add pagination here if necessary)
	      Page<Property> propertyPage = propertyService.getAllProperties(0, 10); // Adjust pagination as needed 
	      // Convert Page to List
	      List<Property> properties = propertyPage.getContent();     
	      model.addAttribute("properties", properties);
	      return "userProperties"; // The name of the Thymeleaf template (properties.html)
	  }
    
    
    // Handle the Add Property form submission
    @PostMapping("/properties")
    public String addProperty(@RequestParam(required = false) Integer pId,
                               @RequestParam String name,
                               @RequestParam String location,
                               @RequestParam String description,
                               @RequestParam String type,
                               @RequestParam String area,
                               @RequestParam String currency,
                               Model model) {
    	// Fetch the user with id 1 (since there's only one user)
        User user = userRepository.findById(1)
                                  .orElseThrow(() -> new RuntimeException("User not found"));
        Property property = new Property();
        property.setpId(pId);  // Using Integer propertyId
        property.setName(name);
        property.setLocation(location);
        property.setDescription(description);
        property.setType(type);
        property.setArea(area);
        property.setCurrency(currency);
        property.setUser(user);  // Set the foreign key relationship to user with id = 1
        System.out.println(user);
        
        propertyService.addProperty(property);

        model.addAttribute("message", "Property added successfully!");
        return "redirect:/user/userProperties"; // Redirect back to the form page
    }

    // Handle the Upload Photos form submission
    @PostMapping("/properties/upload")
    public String uploadPhotos(@RequestParam Integer property_p_id,
                               @RequestParam List<MultipartFile> photos,
                               Model model) {
        try {
            propertyService.uploadPhotos(property_p_id, photos); // Use Integer propertyIdUpload
            model.addAttribute("message", "Photos uploaded successfully!");
        } catch (IOException e) {
            model.addAttribute("message", "Failed to upload photos.");
        }
        return "redirect:/user/userProperties"; // Redirect back to the form page
    }

    @GetMapping("/reviews")
    public String listReviews(Model model) {
        // Fetch all reviews without pagination or sorting
        List<Review> reviews = reviewService.getAllReviews();

        // Add the reviews to the model
        model.addAttribute("reviews", reviews);
        model.addAttribute("page", "reviews");
        return "reviews"; // This will display the reviews without pagination and sorting
    }

 // Delete a review by its ID
    @GetMapping("/reviews/delete/{id}")
    public String deleteaReview(@PathVariable("id") Long id) {
        reviewService.deleteReview(id);
        return "redirect:/user/reviews";  // Redirect back to the list of reviews after deletion
    }
    
 // Show all reviews (both approved and pending)
    @GetMapping("/adminreviews")
    public String getAllReviews(Model model) {
        List<Review> reviews = reviewService.getAllReviews();
        model.addAttribute("reviews", reviews);
        model.addAttribute("page", "areviews");
        return "adminreviews";  // Thymeleaf template name
    }

    // Approve a review by ID
    @PostMapping("/{id}/approve")
    public String approveReview(@PathVariable Long id) {
        Review review = reviewService.getAllReviews().stream()
            .filter(r -> r.getId().equals(id))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Invalid review Id:" + id));
        review.setApproved(true);
        reviewService.saveReview(review);
        return "redirect:/user/adminreviews";
    }

    // Delete a review by ID
    @PostMapping("/{id}/delete")
    public String deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return "redirect:/user/adminreviews";
    }
}
