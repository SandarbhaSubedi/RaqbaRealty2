package com.smart.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    private String email;

    private String location;

    @Column(length = 500) // Limiting review message length
    private String reviewMessage;

    private LocalDateTime submittedAt;
    
    private Integer starRating; // New field for star rating
    
    //field for admin approval status
    private boolean approved = false;
    
    @Transient
    private String starRepresentation;

    public String getStarRepresentation() {
        return starRepresentation;
    }

    public void setStarRepresentation(String starRepresentation) {
        this.starRepresentation = starRepresentation;
    }


    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getReviewMessage() {
        return reviewMessage;
    }

    public void setReviewMessage(String reviewMessage) {
        this.reviewMessage = reviewMessage;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }
    
    // Getters and Setters
    public Integer getStarRating() {
        return starRating;
    }

    public void setStarRating(Integer starRating) {
        this.starRating = starRating;
    }

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}
    
    
}

