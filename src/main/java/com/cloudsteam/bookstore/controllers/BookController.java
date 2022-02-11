package com.cloudsteam.bookstore.controllers;

import com.cloudsteam.bookstore.entities.Book;
import com.cloudsteam.bookstore.services.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/books")
@RestController
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public Book create(@RequestBody() Book book) {
        return bookService.create(book);
    }

    @GetMapping
    public List<Book> getAll() {
        return bookService.getAll();
    }

    @GetMapping("/{id}")
    public Book getBook(@PathVariable("id") int bookId)
    {
        return bookService.findById(bookId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int bookId)
    {
        bookService.delete(bookId);
    }

    @PutMapping("/{id}")
    public Book update(@PathVariable int id, @RequestBody() Book book){
        return bookService.update(book);
    }
}
