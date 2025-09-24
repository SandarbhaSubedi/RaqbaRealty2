package com.smart.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smart.dao.ContactRepository;
import com.smart.dao.UserRepository;
import com.smart.entities.Contact;
import com.smart.entities.Property;
import com.smart.entities.User;

@Service
public class ContactService {

    private final ContactRepository contactRepository;
    private final UserRepository userRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository, UserRepository userRepository) {
        this.contactRepository = contactRepository;
        this.userRepository = userRepository;
    }

    //For saving contacts given by unknown user(working)
    public void saveContact(Contact contact) {
        if (contact != null) {
            // Fetch the User with ID 1 (assuming 1 is the user_id you want to use)
            User user = userRepository.findById(1).orElse(null);
            
            if (user != null) {
                // Set the user for the contact
                contact.setUser(user);
                // Save the contact with the user_id set
                contactRepository.save(contact);
            } else {
                throw new IllegalStateException("User with ID 1 not found");
            }
        } else {
            throw new IllegalArgumentException("Contact cannot be null");
        }
    }

    

    
    
    //for showing all contacts(working)
    public List<Contact> getAllContacts() {
        
        return contactRepository.findAll();
    }
    
    //for giving(updating) complete status on contacts(working)
    public void updateContactStatus(Long id, String status) {
        Contact contact = contactRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Contact not found"));
        contact.setStatus(status);
        contactRepository.save(contact);
    }
    
    
    
    
  //for removing specific contact row(working)
    @Transactional
    public void deleteContact(Long contactId) {
        Optional<Contact> contact = contactRepository.findById(contactId);
        if (contact.isPresent()) {
            Contact contactToDelete = contact.get();
            
            // If contact has a user associated, dissociate or delete contact
            if (contactToDelete.getUser() != null) {
                // Optionally, you can also remove or update user to break the foreign key constraint
                contactToDelete.setUser(null); // This breaks the foreign key relationship
                contactRepository.save(contactToDelete); // Update contact if needed
            }

            // Now delete the contact
            contactRepository.delete(contactToDelete);
        } else {
            throw new RuntimeException("Contact not found with ID: " + contactId);
        }
    }
    


}
