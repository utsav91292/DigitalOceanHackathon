package com.baymax.hackathon.service;

import com.baymax.hackathon.model.Subscriber;
import com.baymax.hackathon.repository.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by npanthi on 11/11/2017.
 */
@Service
public class SubscriberService {
    @Autowired
    private SubscriberRepository subscriberRepository;

    public Subscriber createSubscriber(Subscriber subscriber) {
        return subscriberRepository.save(subscriber);
    }

    public List<Subscriber> getAllSubscribers() {
        return (List<Subscriber>) subscriberRepository.findAll();
    }

    public Subscriber getSubscriber(Long subscriberId) {
        return subscriberRepository.findOne(subscriberId);
    }
}
