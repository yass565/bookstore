package com.cloudsteam.bookstore.services;

import com.cloudsteam.bookstore.entities.Book;
import com.cloudsteam.bookstore.entities.Comment;
import com.cloudsteam.bookstore.repositories.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    BookRepository bookRepository;

    @InjectMocks
    BookService bookService;

    Book book;
    List<Comment> comments;

    @BeforeEach
    void initData() {

        comments = new ArrayList<>();
        comments.add(new Comment(1, "Livre très intéressant"));
        comments.add(new Comment(2, "Riche en contenu"));

        book=new Book(1, "Programmation en C", comments);

    }

    @Test
    public void should_create_a_book() {

        given(bookRepository.save(new Book(1, "Programmation en C", comments))).willAnswer(invocation ->invocation.getArgument(0));

        Book savedBook = bookService.createBook(new Book(1, "Programmation en C", comments));


        assertThat(savedBook).isNotNull();
        // verify if the save method is called when createBook is called too
        verify(bookRepository, times(1)).save(any(Book.class));

    }



    @Test
    public void should_get_all_books() {
        List<Book> books = new ArrayList<>();
        books.add(new Book(1, "Programmation en C", comments));
        books.add(new Book(2, "Java", comments));
        books.add(new Book(3, "Spring boot", comments));


        given(bookRepository.findAll()).willReturn(books);
        List<Book> expected=bookService.getAllBooks();
        assertThat(expected).isEqualTo(books);
    }

    @Test
    public void should_get_book_by_id() {
        Book book = new Book(1, "Programmation en C", comments);
        given(bookRepository.findById(book.getUuid())).willReturn(java.util.Optional.of(book));

        Book returned = bookService.getBookById(book.getUuid());

        verify(bookRepository, times(1)).findById(1);
        verifyNoMoreInteractions(bookRepository);

        assertThat(returned).isEqualTo(book);
    }

    @Test
    public void should_update_book(){

        Book updated = new Book(1, "Programmation en C", comments);
        when(bookRepository.save(updated)).thenReturn(updated);
        Book returned = bookService.updateBook(updated);

        assertThat(returned).isEqualTo(book);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

}
