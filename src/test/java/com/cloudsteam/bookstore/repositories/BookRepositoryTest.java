package com.cloudsteam.bookstore.repositories;

import com.cloudsteam.bookstore.entities.Book;
import com.cloudsteam.bookstore.entities.Comment;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BookRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BookRepository bookRepository;

    Book book;
    List<Comment> comments;

    @BeforeAll
    void initData() {
        comments = new ArrayList<Comment>();
        comments.add(new Comment(1, "Livre très interessant"));
        comments.add(new Comment(2, "Riche en contenu"));

        book=new Book(1, "Programmation en C", comments);

    }

    @Test
    public void should_save_a_book() {
        Book book = bookRepository.save(new Book(1, "Programmation en C", comments));

        assertThat(book).hasFieldOrPropertyWithValue("uuid", 1);
        assertThat(book).hasFieldOrPropertyWithValue("name", "Programmation en C");
        assertThat(book).hasFieldOrPropertyWithValue("comments", comments);
    }


}
