package com.example.test02.services;

import com.example.test02.entities.Book;

import java.util.List;

public interface BookService {
    Book addBook(Book book);
    boolean deleteBook(int id);
    Book updateBook(Book book);
    List<Book> getAllBooks();
    Book getBookById(int id);
}
