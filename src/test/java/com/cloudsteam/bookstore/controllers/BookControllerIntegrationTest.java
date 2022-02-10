package com.cloudsteam.bookstore.controllers;

import com.cloudsteam.bookstore.BookstoreApplication;
import com.cloudsteam.bookstore.entities.Book;
import com.cloudsteam.bookstore.entities.Comment;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = BookstoreApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    HttpHeaders headers = new HttpHeaders();

    String baseUrl="http://localhost:"+port;


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
public void testAddBook(){
    List<Comment> comments = new ArrayList<>();
    comments.add(new Comment(1, "Livre très intéressant"));
    comments.add(new Comment(2, "Riche en contenu"));
    Book book = new Book(1, "Programmation en C", comments);
    HttpEntity<Book> entity = new HttpEntity<Book>(book, headers);
    ResponseEntity<String> response = restTemplate.exchange(
            "http://localhost:"+port+"/books", HttpMethod.POST, entity, String.class
    );
    assertThat(response.getStatusCodeValue()).isEqualTo(201);
}

    @Test
    public void testGetAllBooks() {

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:"+port+"/books",
                HttpMethod.GET, entity, String.class);

        String expected = "[{\"uuid\":1,\"name\":\"Programmation en C\",\"comments\":[{\"id\":1,\"comment\":\"Livre très intéressant\"},{\"id\":2,\"comment\":\"Riche en contenu\"}]}]";

        assertThat(response.getBody()).isEqualTo(expected);
    }



    @Test
    public void returnABookWithIdOne() {

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:"+port+"/books/1",
                HttpMethod.GET, entity, String.class);
        String expected = "{\"uuid\":1,\"name\":\"Programmation en C\",\"comments\":[{\"id\":1,\"comment\":\"Livre très intéressant\"},{\"id\":2,\"comment\":\"Riche en contenu\"}]}";

        assertThat(response.getBody()).isEqualTo(expected);

    }


}
