package com.baymax.hackathon.service;

import java.util.List;
import java.util.Set;

import com.baymax.hackathon.model.Booking;
import com.baymax.hackathon.model.BookingStatus;
import com.baymax.hackathon.model.Publisher;
import com.baymax.hackathon.model.Subscriber;
import com.baymax.hackathon.repository.BookingRepository;
import com.baymax.hackathon.repository.PublisherRepository;
import com.baymax.hackathon.repository.SubscriberRepository;
import com.baymax.hackathon.util.MailSender;

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
    
    @Autowired
    private MailSender mailSender;
    @Autowired
    private SubscriptionService subscriptionService;

    public void lockBooking(long bookingId, long subscriberId) {
        Booking booking = bookingRepository.findOne(bookingId);
        booking.setSubscriberId(subscriberId);
        booking.setBookingStatus(BookingStatus.BOOKED);
        bookingRepository.save(booking);
    }

    public void cancelBooking(long bookingId) {
        Booking booking = bookingRepository.findOne(bookingId);
        booking.setBookingStatus(BookingStatus.AVAILABLE);
        bookingRepository.save(booking);
    }

    public void createNewBooking(Booking booking, long publisherId) {
        booking.setPublisherId(publisherId);
        booking.setBookingStatus(BookingStatus.NEW);
        bookingRepository.save(booking);
    }

    public void publishBooking(long bookingId) {
        Booking booking = bookingRepository.findOne(bookingId);
        Set<Subscriber> subscribersForPublishers = subscriptionService.getSubscribersForPublishers(booking.getPublisherId());
        Publisher publisher = publisherRepository.findOne(booking.getPublisherId());
        for (Subscriber subscriber : subscribersForPublishers) {
        	mailSender.sendMail(booking, subscriber.getEmail(), publisher);
		}
        booking.setBookingStatus(BookingStatus.AVAILABLE);
        bookingRepository.save(booking);
        
    }

    public void deliverBooking(Long bookingId) {
        Booking booking = bookingRepository.findOne(bookingId);
        booking.setBookingStatus(BookingStatus.DELIVERED);
        bookingRepository.save(booking);
    }

    public List<Booking> findByPublisher(long publisherId) {
        return bookingRepository.findByPublisherId(publisherId);
    }

    public List<Booking> findBySubscriber(long subscriberId) {
        return bookingRepository.findBySubscriberId(subscriberId);
    }
}
