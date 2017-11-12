package com.baymax.hackathon.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.baymax.hackathon.model.json.Subscription;

/**
 * Created by npanthi on 11/11/2017.
 */
public interface SubscriptionRepository extends CrudRepository<Subscription, Long> {
    Subscription findBySubscriberIdAndPublisherId(long subscriberId, long publisherId);
    List<Subscription> findByPublisherId(long publisherId);
}
