package com.cloudsteam.bookstore.controllers;

import com.cloudsteam.bookstore.entities.Book;
import com.cloudsteam.bookstore.entities.Comment;
import com.cloudsteam.bookstore.services.BookService;
import com.cloudsteam.bookstore.utils.ObjectMapperUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BookController.class)
class BookControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BookService bookService;


    ObjectMapperUtils objectMapper = new ObjectMapperUtils();


    @Test
    void should_create_a_book() throws Exception{

        //Given
        var uri = "/books";
        var comments = List.of(new Comment(1, "Livre très intéressant"), new Comment(2, "Riche en contenu"));
        var book = new Book(1, "Programmation en C", comments);
        var inputJson = objectMapper.mapToJson(book);

        //When
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void should_return_all_books() throws Exception {
        //Given
        var uri = "/books";
        var commentsList1 = List.of(new Comment(1, "Livre très intéressant"), new Comment(2, "Riche en contenu"));
        var book1 = new Book(1, "Programmation en C", commentsList1);

        var commentsList2 = List.of(new Comment(3, "Livre très intéressant"), new Comment(4, "Riche en contenu"));
        var book2 = new Book(2, "Programmation en C", commentsList2);

        var commentsList3 = List.of(new Comment(5, "Livre très intéressant"), new Comment(6, "Riche en contenu"));
        var book3 = new Book(3, "Programmation en C", commentsList3);

        var books = List.of(book1, book2, book3);
        //when
        when(bookService.getAll()).thenReturn(books);
        mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

    }

    @Test
    void should_find_book_by_id() throws Exception {

        //Given
        var comments = List.of(new Comment(1, "Livre tres interessant"), new Comment(2, "Riche en contenu"));
        var book = new Book(1, "Programmation en C", comments);

        // When
        when(bookService.findById(any(Integer.class))).thenReturn(book);
        RequestBuilder reqBuilder = MockMvcRequestBuilders.get("/books/1");
        var result = mockMvc.perform(reqBuilder)
                .andExpect(status().isOk())
                .andReturn();
        var responseJson = result.getResponse().getContentAsString();
        var responseExpectedJson = objectMapper.mapToJson(book);

        // Then
        assertThat(responseJson).isEqualTo(responseExpectedJson);
    }

    @Test
    void should_update_book() throws Exception {
        //Given
        var uri = "/books/1";
        var comments = List.of(new Comment(1, "Livre tres interessant"), new Comment(2, "Riche en contenu"));
        var book = new Book(1, "Programmation en C", comments);
        book.setName("La semaine de quatre heures");
        var inputJson = objectMapper.mapToJson(book);

        //When
        when(bookService.update(any())).thenReturn(book);
        var mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andExpect(status().isOk()).andReturn();

        //Then
        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo(objectMapper.mapToJson(book));

    }

    @Test
    void should_delete_a_book() throws Exception {

        //When
        mockMvc.perform(MockMvcRequestBuilders.delete("/books/2"))
                .andExpect(status().isOk())
                .andReturn();
    }
}
