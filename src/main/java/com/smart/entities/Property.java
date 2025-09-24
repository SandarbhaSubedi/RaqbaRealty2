package com.smart.entities;



import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="PROPERTY")
public class Property {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int pId;
	private String name;
	private String location;
	private String type;
	private String currency;
	@ElementCollection
    @Lob
    private List<byte[]> photos = new ArrayList<>();
	@Column(length = 500)
	private String description;
	private String area;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
	
	public int getpId() {
		return pId;
	}



	public void setpId(int pId) {
		this.pId = pId;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getLocation() {
		return location;
	}



	public void setLocation(String location) {
		this.location = location;
	}



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



	public String getCurrency() {
		return currency;
	}



	public void setCurrency(String currency) {
		this.currency = currency;
	}



	public List<byte[]> getPhotos() {
        return photos;
    }

    public void setPhotos(List<byte[]> photos) {
        this.photos = photos;
    }



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}


	public String getArea() {
		return area;
	}



	public void setArea(String area) {
		this.area = area;
	}
	
	 public User getUser() {
	        return user;
	    }

	    public void setUser(User user) {
	        this.user = user;  // This should work as long as User is correctly instantiated
	    }


	@Override
	public boolean equals(Object obj) {
		return this.pId==((Property)obj).getpId();	}
	
	

	
}
