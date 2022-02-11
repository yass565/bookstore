package com.cloudsteam.bookstore.controllers;

import com.cloudsteam.bookstore.entities.Book;
import com.cloudsteam.bookstore.entities.Comment;
import com.cloudsteam.bookstore.repositories.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    BookRepository bookRepository;

    @Test
    void testAddBook(){

        //given
        var comments = List.of(new Comment(1, "Livre très intéressant"), new Comment(2, "Riche en contenu") );
        var book = new Book(1, "Programmation en C", comments);

        //when
        var response = restTemplate.postForEntity("http://localhost:"+port+"/books", book, Book.class );

        //Then
        assertThat(response.getBody()).isEqualTo(book);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    void testGetAllBooks() {
        //given
        var comments = List.of(new Comment(1, "Livre très intéressant"), new Comment(2, "Riche en contenu") );
        var book = new Book(1, "Programmation en C", comments);

        //when
        bookRepository.save(book);
        var actual = restTemplate.getForObject("http://localhost:" + port + "/books", Book[].class);
        var expected = List.of(book).toArray();

        //Then
        assertThat(actual).isEqualTo(expected);
    }



    @Test
    void should_return_book_with_id_one() {

        //given
        var comments = List.of(new Comment(1, "Livre très intéressant"), new Comment(2, "Riche en contenu") );
        var book=bookRepository.save(new Book(1, "Programmation en C", comments));

        //when
        var response = restTemplate.getForEntity("http://localhost:"+port+"/books/1", Book.class, String.class, 1);

        //Then
        assertThat(response.getBody()).isEqualTo(book);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }


}
