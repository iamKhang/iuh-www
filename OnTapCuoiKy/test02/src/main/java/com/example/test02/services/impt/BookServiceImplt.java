package com.example.test02.services.impt;

import com.example.test02.entities.Book;
import com.example.test02.services.BookService;
import com.example.test02.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImplt implements BookService {

    private BookRepository bookRepository;

    @Autowired
    public BookServiceImplt(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    @Override
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public boolean deleteBook(int id) {
        return bookRepository.deleteBookById(id);
    }

    @Override
    public Book updateBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(int id) {
        return bookRepository.findById(id).orElse(null);
    }
}
