package com.baymax.hackathon.repository;

import com.baymax.hackathon.model.Booking;
import org.springframework.data.repository.CrudRepository;

public interface BookingRepository extends CrudRepository<Booking, Long>{

}
