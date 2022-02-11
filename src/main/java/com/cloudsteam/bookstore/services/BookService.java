package com.cloudsteam.bookstore.services;

import com.cloudsteam.bookstore.entities.Book;
import com.cloudsteam.bookstore.repositories.BookRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {


    BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book create(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    public Book findById(int id)
    {
        return bookRepository.findById(id).get();
    }

    public void delete(int id)
    {
        bookRepository.deleteById(id);
    }

    public Book update(Book book) { return bookRepository.save(book); }

}
