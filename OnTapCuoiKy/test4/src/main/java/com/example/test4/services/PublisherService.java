package com.example.test4.services;

import com.example.test4.entities.Publisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PublisherService {
    List<Publisher> getAllPublisher();

}
