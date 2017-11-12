package com.baymax.hackathon.endpoint;

import java.util.List;

import com.baymax.hackathon.model.json.Subscription;
import com.baymax.hackathon.service.SubscriptionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by npanthi on 11/11/2017.
 */
@RestController
public class SubscriptionEndpoint {
    @Autowired
    private SubscriptionService subscriptionService;

    @RequestMapping(
            value = "/subscriber/{subscriberId}/publisher/{publisherId}",
            method = RequestMethod.POST
    )
    public void createSubscription(@PathVariable Long subscriberId, @PathVariable Long publisherId) {
        subscriptionService.subscribe(subscriberId, publisherId);
    }

    @RequestMapping(
            value = "/subscriber/{subscriberId}/publisher/{publisherId}",
            method = RequestMethod.DELETE
    )
    public void removeSubscription(@PathVariable Long subscriberId, @PathVariable Long publisherId) {
        subscriptionService.unsubscribe(subscriberId, publisherId);
    }

    @RequestMapping(
            value = "/publisher/{publisherId}/approval/subscriber/{subscriberId}",
            method = RequestMethod.PUT
    )
    public void approveSubscription(@PathVariable Long subscriberId, @PathVariable Long publisherId, @RequestBody Subscription subscription)
    {
        subscriptionService.approve(subscriberId, publisherId);
    }
    
    @RequestMapping(
            value = "/publisher/{publisherId}/subscribers",
            method = RequestMethod.GET
    )
    public List<Subscription> getSubscriptions(@PathVariable Long publisherId)
    {
        return subscriptionService.getSubscriptions(publisherId);
    }
}
