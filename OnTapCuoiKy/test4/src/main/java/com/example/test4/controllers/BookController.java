package com.example.test4.controllers;

import com.example.test4.entities.Book;
import com.example.test4.services.BookService;
import com.example.test4.services.PublisherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final PublisherService publisherService;

    @GetMapping({"/", "/books"})
    public String books(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "index";
    }

    @GetMapping("/books/delete/{id}")
    public String deletebook(@PathVariable int id, Model model) {
        bookService.deleteBook(id);
        return "redirect:/";
    }

    @PostMapping("/books")
    public String createBook(Model model, @Valid @ModelAttribute Book book, BindingResult result) {
        if (result.hasErrors()) {
            return "add-book";
        }

        bookService.addBook(book);
        return "redirect:/books";
    }

    @GetMapping("/books/add")
    public String createBook(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("publishers", publisherService.getAllPublisher());
        return "add-book";
    }
}
