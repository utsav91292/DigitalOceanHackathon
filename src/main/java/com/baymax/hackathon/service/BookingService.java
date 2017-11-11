package com.baymax.hackathon.service;

import com.baymax.hackathon.model.Booking;
import com.baymax.hackathon.model.BookingStatus;
import com.baymax.hackathon.model.Publisher;
import com.baymax.hackathon.model.Subscriber;
import com.baymax.hackathon.repository.BookingRepository;
import com.baymax.hackathon.repository.PublisherRepository;
import com.baymax.hackathon.repository.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by npanthi on 11/11/2017.
 */
@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private SubscriberRepository subscriberRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    public void lockBooking(long bookingId, long subscriberId) {
        Booking booking = bookingRepository.findOne(bookingId);
        Subscriber subscriber = subscriberRepository.findOne(subscriberId);
        booking.setSubscriber(subscriber);
        booking.setBookingStatus(BookingStatus.BOOKED);
        bookingRepository.save(booking);
    }

    public void cancelBooking(long bookingId) {
        Booking booking = bookingRepository.findOne(bookingId);
        booking.setBookingStatus(BookingStatus.AVAILABLE);
        bookingRepository.save(booking);
    }

    public void createNewBooking(Booking booking, long publisherId) {
        Publisher publisher = publisherRepository.findOne(publisherId);
        booking.setPublisher(publisher);
        booking.setBookingStatus(BookingStatus.NEW);
        bookingRepository.save(booking);
    }

    public void publishBooking(long bookingId) {
        Booking  booking = bookingRepository.findOne(bookingId);
        booking.setBookingStatus(BookingStatus.AVAILABLE);
        bookingRepository.save(booking);
    }

    public void deliverBooking(Long bookingId) {
        Booking booking = bookingRepository.findOne(bookingId);
        booking.setBookingStatus(BookingStatus.DELIVERED);
        bookingRepository.save(booking);
    }
}
