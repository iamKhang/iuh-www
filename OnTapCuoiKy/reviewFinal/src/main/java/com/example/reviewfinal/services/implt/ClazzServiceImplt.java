package com.example.reviewfinal.services.implt;

import com.example.reviewfinal.entities.Clazz;
import com.example.reviewfinal.repositories.ClazzRepository;
import com.example.reviewfinal.services.ClazzService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ClazzServiceImplt implements ClazzService {

    private final ClazzRepository repository;

    @Override
    public List<Clazz> findAll() {
        return repository.findAll();
    }
}
