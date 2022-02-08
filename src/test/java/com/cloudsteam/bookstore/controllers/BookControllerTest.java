package com.cloudsteam.bookstore.controllers;

import com.cloudsteam.bookstore.entities.Book;
import com.cloudsteam.bookstore.entities.Comment;
import com.cloudsteam.bookstore.services.BookService;
import com.cloudsteam.bookstore.utils.ObjectMapperUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = BookController.class)
class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    BookService bookService;


    @Autowired
    WebApplicationContext webApplicationContext;

    protected void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    private List<Comment> comments;
    ObjectMapperUtils objectMapper = new ObjectMapperUtils();


    @Test
    public void should_create_a_book() throws Exception{
        String uri = "/books";
        comments = new ArrayList<Comment>();
        comments.add(new Comment(1, "Livre très interessant"));
        comments.add(new Comment(2, "Riche en contenu"));
        Book book = new Book(1, "Programmation en C", comments);


        String inputJson = objectMapper.mapToJson(book);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertThat(status).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    public void should_return_all_books() throws Exception {
        String uri = "/books";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertThat(status).isEqualTo(200);
        String content = mvcResult.getResponse().getContentAsString();
        Book[] books = objectMapper.mapFromJson(content, Book[].class);
        assertThat(books.length > 0);
    }

    @Test
    public void should_update_book() throws Exception {
        String uri = "/Books/2";
        Book Book = new Book();
        Book.setName("Lemon");

        String inputJson = objectMapper.mapToJson(Book);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content, "Book is updated successsfully");
    }

    @Test
    public void deleteBook() throws Exception {
        String uri = "/books/2";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content, "Book is deleted successsfully");
    }
}
