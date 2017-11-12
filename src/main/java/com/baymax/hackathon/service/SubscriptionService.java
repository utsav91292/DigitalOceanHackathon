package com.baymax.hackathon.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baymax.hackathon.model.Publisher;
import com.baymax.hackathon.model.Subscriber;
import com.baymax.hackathon.model.json.Subscription;
import com.baymax.hackathon.model.json.SubscriptionStatus;
import com.baymax.hackathon.repository.PublisherRepository;
import com.baymax.hackathon.repository.SubscriberRepository;
import com.baymax.hackathon.repository.SubscriptionRepository;

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
        Subscription  subscription = subscriptionRepository.findBySubscriberIdAndPublisherId(subscriber_id, publisherId);

        if(subscription == null) {
            subscription = new Subscription();
            subscription.setPublisherId(publisherId);
            subscription.setSubscriberId(subscriber_id);
            subscription.setStatus(SubscriptionStatus.PENDING);
            subscription.setSubscriptionDate(new Date());
        }
        subscriptionRepository.save(subscription);
        return subscription;
    }

    public void unsubscribe(long subscriberId, long publisherId) {
        Subscription subscription = subscriptionRepository.findBySubscriberIdAndPublisherId(subscriberId, publisherId);

        subscriptionRepository.delete(subscription);
    }

    public List<Publisher> listPublishers() {
        return (List<Publisher>) publisherRepository.findAll();
    }

    public void approve(Long subscriberId, Long publisherId) {
        Subscription subscription = subscriptionRepository.findBySubscriberIdAndPublisherId(subscriberId, publisherId);
        subscription.setStatus(SubscriptionStatus.APPROVED);
        subscriptionRepository.save(subscription);
    }
    
    public Set<Subscriber> getSubscribersForPublishers(long publisherid) {
    	List<Subscription> subscriptionsForPublishers = subscriptionRepository.findByPublisherId(publisherid);
    	Set<Subscriber> subscribers = new HashSet<Subscriber>();
    	for (Subscription subscription : subscriptionsForPublishers) {
    		subscribers.add(subscriberRepository.findOne(subscription.getSubscriberId()));
		}
    	return subscribers;
    }

	public List<Subscription> getSubscriptions(Long publisherId) {
		return subscriptionRepository.findByPublisherId(publisherId);
	}
}
