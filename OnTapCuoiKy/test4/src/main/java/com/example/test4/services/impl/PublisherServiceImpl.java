package com.example.test4.services.impl;

import com.example.test4.entities.Publisher;
import com.example.test4.repositories.PublisherRepository;
import com.example.test4.services.PublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository publisherRepository;

    @Override
    public List<Publisher> getAllPublisher() {
        return publisherRepository.findAll();
    }
}
