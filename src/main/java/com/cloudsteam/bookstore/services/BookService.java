package com.cloudsteam.bookstore.services;

import com.cloudsteam.bookstore.entities.Book;
import com.cloudsteam.bookstore.exceptions.BookNotFoundException;
import com.cloudsteam.bookstore.repositories.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookService.class);

    @Autowired
    BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<Book>();
        bookRepository.findAll().forEach(books1 -> books.add(books1));
        return books;
    }

    public Book getBookById(int id)
    {
        return bookRepository.findById(id).get();
    }

    public void deleteBook(int id)
    {
        bookRepository.deleteById(id);
    }

    public Book updateBook(Book book) throws BookNotFoundException {
        if(!bookRepository.existsById(book.getUuid())){
            LOGGER.debug("No book found with id: " + book.getUuid());
            throw new BookNotFoundException("Book with id " +  book.getUuid() + " is not found");
        }
        return bookRepository.save(book);
    }

}
