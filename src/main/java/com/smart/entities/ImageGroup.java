package com.smart.entities;



import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "image_groups")
public class ImageGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageId; // Unique identifier for the group
    private String description;
    private String name;
    private String title;//primary or secondary
    private String type; //buy or rent
    private String bedroom;
    private String currency;
    private String location;
    private String furnishing;
    private String feature;
    private String consultant;
    private String area;
    private String faculty; //apartment/house/studio
    private String exclusives;
    private String rerano;
    private String referenceno;
    private String dldpno;
    private String field; //residental/commercial/land

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Foreign Key to Admin
    private User user;

    @OneToMany(mappedBy = "imageGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBedroom() {
		return bedroom;
	}

	public void setBedroom(String bedroom) {
		this.bedroom = bedroom;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getFurnishing() {
		return furnishing;
	}

	public void setFurnishing(String furnishing) {
		this.furnishing = furnishing;
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public String getConsultant() {
		return consultant;
	}

	public void setConsultant(String consultant) {
		this.consultant = consultant;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getFaculty() {
		return faculty;
	}

	public void setFaculty(String faculty) {
		this.faculty = faculty;
	}

	public String getExclusives() {
		return exclusives;
	}

	public void setExclusives(String exclusives) {
		this.exclusives = exclusives;
	}

	public String getRerano() {
		return rerano;
	}

	public void setRerano(String rerano) {
		this.rerano = rerano;
	}

	public String getReferenceno() {
		return referenceno;
	}

	public void setReferenceno(String referenceno) {
		this.referenceno = referenceno;
	}

	public String getDldpno() {
		return dldpno;
	}

	public void setDldpno(String dldpno) {
		this.dldpno = dldpno;
	}
	
	

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

  
}
