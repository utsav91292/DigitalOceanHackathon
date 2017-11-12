package com.baymax.hackathon.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by npanthi on 11/11/2017.
 */
@Entity
public class Booking implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long bookingId;
    @Column(name="description")
    private String description;
    @Column(name="booking_status")
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;
    @Column(name = "publisher_id")
    private long publisherId;
    @Column(name = "subscriber_id")
    private long subscriberId;
	public long getBookingId() {
		return bookingId;
	}
	public void setBookingId(long bookingId) {
		this.bookingId = bookingId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BookingStatus getBookingStatus() {
		return bookingStatus;
	}
	public void setBookingStatus(BookingStatus bookingStatus) {
		this.bookingStatus = bookingStatus;
	}
	public long getPublisherId() {
		return publisherId;
	}
	public void setPublisherId(long publisherId) {
		this.publisherId = publisherId;
	}
	public long getSubscriberId() {
		return subscriberId;
	}
	public void setSubscriberId(long subscriberId) {
		this.subscriberId = subscriberId;
	}
    
    
}
