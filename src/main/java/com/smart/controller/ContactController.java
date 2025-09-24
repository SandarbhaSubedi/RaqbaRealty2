package com.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.smart.entities.Contact;
import com.smart.services.ContactService;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class ContactController {

    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    // Show contact form
    @GetMapping("/contact")
    public String showContactForm(Model model) {
        model.addAttribute("contact", new Contact());
        model.addAttribute("page", "contact");
        return "contact";
    }

    // Handle form submission
    @PostMapping("/submitContact")
    public String submitContact(@ModelAttribute Contact contact, Model model ) {
        contactService.saveContact(contact);
        model.addAttribute("successMessage", "Your message has been sent successfully!");
        model.addAttribute("page", "contact");
        return "contactSuccess";
    }
}
