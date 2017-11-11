package com.baymax.hackathon.service;

import com.baymax.hackathon.model.Publisher;
import com.baymax.hackathon.model.Subscriber;
import com.baymax.hackathon.model.json.Subscription;
import com.baymax.hackathon.model.json.SubscriptionStatus;
import com.baymax.hackathon.repository.PublisherRepository;
import com.baymax.hackathon.repository.SubscriberRepository;
import com.baymax.hackathon.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by npanthi on 11/11/2017.
 */
@Service
public class SubscriptionService {
    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private SubscriberRepository subscriberRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public Subscription subscribe(long subscriber_id, long publisherId) {
        Publisher publisher = publisherRepository.findOne(publisherId);
        Subscriber subscriber = subscriberRepository.findOne(subscriber_id);
        Subscription  subscription = subscriptionRepository.findBySubscriberAndPublisher(subscriber, publisher);

        if(subscriber == null) {
            subscription = new Subscription();
            subscription.setPublisher(publisher);
            subscription.setSubscriber(subscriber);
            subscription.setStatus(SubscriptionStatus.PENDING);
            subscription.setSubscriptionDate(new Date());
        }

        return subscription;
    }

    public void unsubscribe(long subscriberId, long publisherId) {
        Publisher publisher = publisherRepository.findOne(publisherId);
        Subscriber subscriber = subscriberRepository.findOne(subscriberId);
        Subscription subscription = subscriptionRepository.findBySubscriberAndPublisher(subscriber, publisher);

        subscriptionRepository.delete(subscription);
    }

    public List<Publisher> listPublishers() {
        return (List<Publisher>) publisherRepository.findAll();
    }

    public void approve(Long subscriberId, Long publisherId) {
        Publisher publisher = publisherRepository.findOne(publisherId);
        Subscriber subscriber = subscriberRepository.findOne(subscriberId);
        Subscription subscription = subscriptionRepository.findBySubscriberAndPublisher(subscriber, publisher);

        subscription.setStatus(SubscriptionStatus.APPROVED);

        subscriptionRepository.save(subscription);
    }
}
