package com.baymax.hackathon.endpoint;

import com.baymax.hackathon.model.Subscriber;
import com.baymax.hackathon.service.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by npanthi on 11/11/2017.
 */
@RestController
public class SubscriberEndpoint {
    @Autowired
    private SubscriberService subscriberService;

    @RequestMapping(method = RequestMethod.POST, value = "/subscriber")
    public Subscriber createSubscriber(@RequestBody Subscriber subscriber) {
        return subscriberService.createSubscriber(subscriber);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/subscribers")
    public List<Subscriber> createSubscriber(){
    	List<Subscriber> allSubscribers = subscriberService.getAllSubscribers();
        return allSubscribers;
    }

}
