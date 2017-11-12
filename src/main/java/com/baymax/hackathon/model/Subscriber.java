package com.baymax.hackathon.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by npanthi on 11/11/2017.
 */
@Entity
public class Subscriber implements Serializable{
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "subscriber_id")
    private Long subscriberId;
    @OneToMany
    private List<Transaction> transactions;
    @Column
    private String name;
    @Column
    private String email;
    @Column
    private String location;
    @Column
    private double latitude;
    @Column
    private double longitude;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(Long subscriberId) {
        this.subscriberId = subscriberId;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
