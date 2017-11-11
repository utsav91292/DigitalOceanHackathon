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
    	Subscriber e = new Subscriber();
    	e.setLatitude(0);
    	e.setLatitude(0);
    	e.setLocation("Location00");
    	e.setName("Location00");
		allSubscribers.add(e);
		Subscriber e1 = new Subscriber();
    	e1.setLatitude(-34.60);
    	e1.setLatitude(-58.38);
    	e1.setLocation("Location01");
    	e1.setName("Location01");
		allSubscribers.add(e1);
        return allSubscribers;
    }

}
