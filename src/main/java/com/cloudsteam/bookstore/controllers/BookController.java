package com.cloudsteam.bookstore.controllers;

import com.cloudsteam.bookstore.entities.Book;
import com.cloudsteam.bookstore.exceptions.BookNotFoundException;
import com.cloudsteam.bookstore.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("books")
@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody(required = true) Book book) {
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{bookId}")
    private Book getBook(@PathVariable("bookId") int bookId)
    {
        return bookService.getBookById(bookId);
    }

    @DeleteMapping("/{bookId}")
    private void deleteBook(@PathVariable("bookId") int bookId)
    {
        bookService.deleteBook(bookId);
    }

    @PutMapping(path = "/{bookId}")
    public ResponseEntity<Book> updateBook(@PathVariable int bookId, @RequestBody(required = true) Book book) throws BookNotFoundException {
        Book bookUpdated = bookService.updateBook(book);
        return new ResponseEntity<>(bookUpdated, HttpStatus.OK);
    }
}
