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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    BookRepository bookRepository;

    @InjectMocks
    BookService bookService;


    @Test
    void should_create_a_book() {

        //Given
        var comments = List.of(new Comment(1, "Livre très intéressant"), new Comment(2, "Riche en contenu"));
        var book=new Book(1, "Programmation en C", comments);
        //When
        when(bookRepository.save(book)).thenReturn(book);

        var savedBook = bookService.create(new Book(1, "Programmation en C", comments));

        //Then
        assertThat(savedBook).isNotNull();
        verify(bookRepository, times(1)).save(any(Book.class));

    }



    @Test
    void should_get_all_books() {
        //Given
        var comments = List.of(new Comment(1, "Livre très intéressant"), new Comment(2, "Riche en contenu"));
        var books = List.of(new Book(1, "Programmation en C", comments), new Book(2, "Java", comments), new Book(3, "Spring boot", comments));

        //When
        when(bookRepository.findAll()).thenReturn(books);
        var expected=bookService.getAll();

        //Then
        assertThat(expected).isEqualTo(books);
    }

    @Test
    void should_get_book_by_id() {
        //Given
        var comments = List.of(new Comment(1, "Livre très intéressant"), new Comment(2, "Riche en contenu"));
        var book = new Book(1, "Programmation en C", comments);

        //When
        when(bookRepository.findById(book.getUuid())).thenReturn(java.util.Optional.of(book));
        var returned = bookService.findById(book.getUuid());

        //Then
        verify(bookRepository, times(1)).findById(1);
        verifyNoMoreInteractions(bookRepository);
        assertThat(returned).isEqualTo(book);
    }

    @Test
    void should_update_book(){

        //Given
        var comments = List.of(new Comment(1, "Livre très intéressant"), new Comment(2, "Riche en contenu"));
        var updated = new Book(1, "Programmation en C", comments);

        //When
        when(bookRepository.save(updated)).thenReturn(updated);
        var returned = bookService.update(updated);

        //Then
        assertThat(returned).isEqualTo(updated);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void should_delete_book(){

        //Given
        var comments = List.of(new Comment(1, "Livre très intéressant"), new Comment(2, "Riche en contenu"));
        var book = new Book(1, "Programmation en C", comments);

        //When
        bookService.delete(book.getUuid());
        var books=bookRepository.findAll();

        //Then
        assertThat(books).isEmpty();
    }

}
