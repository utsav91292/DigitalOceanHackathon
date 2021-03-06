package com.baymax.hackathon.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baymax.hackathon.model.Booking;
import com.baymax.hackathon.service.BookingService;
import com.baymax.hackathon.service.PublisherService;
import com.baymax.hackathon.service.SubscriberService;

import java.util.List;

/**
 * Created by npanthi on 11/11/2017.
 */
@RestController
public class BookingEndpoint {

    @Autowired
    private BookingService bookingService;
    
    @Autowired
    private PublisherService publisherService;

    @Autowired
    private SubscriberService subscriberService;
    
    @RequestMapping(
            value = "/booking/lock/{bookingId}/subscriber/{subscriberId}",
            method = RequestMethod.PUT
    )
    public void lockBooking(@PathVariable long bookingId, @PathVariable long subscriberId) {
        bookingService.lockBooking(bookingId, subscriberId);
    }

    @RequestMapping(
            value = "/booking/{bookingId}/cancel",
            method = RequestMethod.PUT
    )
    public void cancelBooking(long bookingId) {
        bookingService.cancelBooking(bookingId);
    }

    @RequestMapping(
            value = "/booking/publisher/{publisherId}",
            method = RequestMethod.POST
    )
    public void createNewBooking(@RequestBody Booking booking, @PathVariable long publisherId) {
        bookingService.createNewBooking(booking, publisherId);
    }

    @RequestMapping(
            value = "/booking/{bookingId}/publisher/{publisherId}",
            method = RequestMethod.PUT
    )
    public void publishBooking(@PathVariable long bookingId) {
        bookingService.publishBooking(bookingId);
    }

    @RequestMapping(
            value = "/booking/booking/{bookingId}/deliver",
            method = RequestMethod.PUT
    )
    public void deliverBooking(@PathVariable long bookingId) {
        bookingService.deliverBooking(bookingId);
    }

    @RequestMapping(
            value = "/bookings/subscriber/{subscriberId}",
            method = RequestMethod.GET
    )
    public List<Booking> getAllBookingsForSubscriber(@PathVariable long subscriberId) {
        return bookingService.findBySubscriber(subscriberId);
    }


    @RequestMapping(
            value = "/bookings/publisher/{publisherId}",
            method = RequestMethod.GET
    )
    public List<Booking> getAllBookingsForPublisher(@PathVariable long publisherId) {
        return bookingService.findByPublisher(publisherId);
    }
}
