package com.baymax.hackathon.endpoint;

import com.baymax.hackathon.model.Booking;
import com.baymax.hackathon.model.Publisher;
import com.baymax.hackathon.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by npanthi on 11/11/2017.
 */
@RestController("/booking")
public class BookingEndpoint {

    @Autowired
    private BookingService bookingService;

    @RequestMapping(
            value = "{bookingId}/lock/subscriber/{subscriberId}",
            method = RequestMethod.PUT
    )
    public void lockBooking(@PathVariable long bookingId, @PathVariable long subscriberId) {
        bookingService.lockBooking(bookingId, subscriberId);
    }

    @RequestMapping(
            value = "/{bookingId}/cancel",
            method = RequestMethod.PUT
    )
    public void cancelBooking(long bookingId) {
        bookingService.cancelBooking(bookingId);
    }

    @RequestMapping(
            value = "{bookingId}/lock/publisher/{publisherId}",
            method = RequestMethod.PUT
    )
    public void createNewBooking(@RequestBody Booking booking, @PathVariable long publisherId) {
        bookingService.createNewBooking(booking, publisherId);
    }

    @RequestMapping(
            value = "{bookingId}/publish/publisher/{publisherId}",
            method = RequestMethod.PUT
    )
    public void publishBooking(@PathVariable long bookingId) {
        bookingService.publishBooking(bookingId);
    }

    @RequestMapping(
            value = "{bookingId}/deliver",
            method = RequestMethod.PUT
    )
    public void deliverBooking(@PathVariable long bookingId) {
        bookingService.deliverBooking(bookingId);
    }

    @RequestMapping(
            value = "{bookingId}/notify",
            method = RequestMethod.PUT
    )
    public void notify(long bookingId) {

    }
}
