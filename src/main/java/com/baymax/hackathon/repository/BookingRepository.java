package com.baymax.hackathon.repository;

import com.baymax.hackathon.model.Booking;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookingRepository extends CrudRepository<Booking, Long>{

    List<Booking> findByPublisherId(long publisherId);

    List<Booking> findBySubscriberId(long subscriberId);
}
