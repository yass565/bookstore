package com.cloudsteam.bookstore.services;

import com.cloudsteam.bookstore.entities.Book;
import com.cloudsteam.bookstore.entities.Comment;
import com.cloudsteam.bookstore.repositories.BookRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@RunWith(SpringRunner.class)
public class BookServiceTest {

    @Mock
    BookRepository bookRepository;

    @InjectMocks
    BookService bookService;

    Book book;
    List<Comment> comments;

    @BeforeAll
    void initData() {
        bookService = new BookService(bookRepository);
        comments = new ArrayList<Comment>();
        comments.add(new Comment(1, "Livre très interessant"));
        comments.add(new Comment(2, "Riche en contenu"));

        book=new Book(1, "Programmation en C", comments);

    }

    @Test
    public void should_create_a_book() {


        bookService.createBook(new Book(1, "Programmation en C", comments));

        // verify if the save method is called when createBook is called too
        verify(bookRepository, times(1)).save(any(Book.class));

    }

}
