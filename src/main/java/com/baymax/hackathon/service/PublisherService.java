package com.baymax.hackathon.service;

import com.baymax.hackathon.model.Publisher;
import com.baymax.hackathon.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by npanthi on 11/11/2017.
 */
@Service
public class PublisherService {
    @Autowired
    private PublisherRepository publisherRepository;

    public Publisher createPublisher(Publisher publisher) {
        return publisherRepository.save(publisher);
    }

    public List<Publisher> getAllPublishers()  {
        return (List<Publisher>)publisherRepository.findAll();
    }
}
