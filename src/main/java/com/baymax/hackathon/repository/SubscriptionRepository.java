package com.baymax.hackathon.repository;

import com.baymax.hackathon.model.Publisher;
import com.baymax.hackathon.model.Subscriber;
import com.baymax.hackathon.model.json.Subscription;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by npanthi on 11/11/2017.
 */
public interface SubscriptionRepository extends CrudRepository<Subscription, Long> {
    Subscription findBySubscriberAndPublisher(Subscriber subscriber, Publisher publisher);
}
