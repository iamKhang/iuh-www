package com.example.test4.services;

import com.example.test4.entities.Book;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();
    Book getBookById(int id);
    Book addBook(Book book);
    Book updateBook(Book book);
    int deleteBook(int id);
}
