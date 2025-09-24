package com.smart.entities;

import java.util.ArrayList;
import java.util.List;

import com.smart.entities.ImageGroup;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "USERS") // Renamed to USERS to avoid confusion with SQL keywords
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int user_id;

    @NotBlank(message = "Name field is required")
    @Size(min = 2, max = 20, message = "Minimum 2 and maximum 20 characters are allowed")
    private String name;

    @Column(unique = true)
    @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "Enter a valid email")
    private String email;

    @NotBlank(message = "Password field is required")
    private String password;

    private String role = "ADMIN"; // Default role for this model is ADMIN
    private boolean enabled = true;
    private String imageUrl;

 
    

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Property> properties = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImageGroup> imageGroups; // Reference ImageGroup instead of Image

    public User() {
        super();
    }

    // Getters and Setters
    public int getId() {
        return user_id;
    }

    public void setId(int id) {
        this.user_id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }
    
    public List<ImageGroup> getImageGroups() { return imageGroups; }
    public void setImageGroups(List<ImageGroup> imageGroups) { this.imageGroups = imageGroups; }

    @Override
    public String toString() {
        return "User [id=" + user_id + ", name=" + name + ", email=" + email + ", role=" + role + ", enabled=" + enabled
                + ", imageUrl=" + imageUrl + "]";
    }
}
