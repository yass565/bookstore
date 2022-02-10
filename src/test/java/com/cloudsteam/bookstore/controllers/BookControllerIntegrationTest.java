package com.cloudsteam.bookstore.controllers;

import com.cloudsteam.bookstore.BookstoreApplication;
import com.cloudsteam.bookstore.entities.Book;
import com.cloudsteam.bookstore.entities.Comment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = BookstoreApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

/*
    @Test
    public void testAllBooks()
    {
        assertThat(
                this.restTemplate
                        .getForObject("http://localhost:" + port + "/books", Book[].class));
    }

 */

    @Test
    public void testAddBook() {
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment(1, "Livre très intéressant"));
        comments.add(new Comment(2, "Riche en contenu"));
        Book book = new Book(1, "Programmation en C", comments);
        ResponseEntity<String> responseEntity = this.restTemplate
                .postForEntity("http://localhost:" + port + "/books", book, String.class);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
    }

    @Test
    public void returnABookWithIdOne() {

        Book book = this.restTemplate.getForObject("http://localhost:" + port + "/books/{id}", Book.class, 1);
        assertThat(book).isNotNull();

    }
/*
    @Test
    public void return404() {
        ResponseEntity<String> err = restTemplate.getForEntity
                ("http://localhost:" + port + "/books/{id}", String.class, 5);
        assertThat(err).isNotNull();
        assertThat(err.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(err.getBody()).isNull();
    }

 */
}
