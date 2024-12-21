package com.example.test4.services.impl;

import com.example.test4.entities.Book;
import com.example.test4.repositories.BookRepository;
import com.example.test4.repositories.PublisherRepository;
import com.example.test4.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImp implements BookService {

    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(int id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public int deleteBook(int id) {
        if(bookRepository.existsById(id)){
            bookRepository.deleteById(id);
            return 1;
        }
        return 0;
    }
}
