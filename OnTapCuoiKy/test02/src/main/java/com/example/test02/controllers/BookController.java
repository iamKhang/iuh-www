package com.example.test02.controllers;

import com.example.test02.entities.Book;
import com.example.test02.services.BookService;
import com.example.test02.utils.ISBNGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BookController {
    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String index(Model model) {

        bookService.getAllBooks().forEach(System.out::println);
        model.addAttribute("books", bookService.getAllBooks());

        return "index";
    }

    @GetMapping("/books/add")
    public String showFormAdd(Model model) {
        model.addAttribute("book", new Book());
        return "add-book";
    }

    @PostMapping("/books/add")
    public String addBook(@ModelAttribute Book book, Model model) {

        try {
            book.setIsbn(ISBNGenerator.generateISBN());
            bookService.addBook(book);
            return "redirect:/";
        } catch (Exception e) {
            return "redirect:/books/add";
        }


    }
}

