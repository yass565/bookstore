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
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

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

    @Test
    public void should_find_all_books() {
        Book book1 = new Book(1, "Programmation en C", comments);
        entityManager.persist(book1);
        Book book2 = new Book(2, "Programmation en C", comments);
        entityManager.persist(book2);
        Book book3 = new Book(3, "Programmation en C", comments);
        entityManager.persist(book3);
        Iterable<Book> books = bookRepository.findAll();
        assertThat(books).hasSize(3).contains(book1, book2, book3);
    }

    @Test
    public void should_find_book_by_id() {
        Book book1 = new Book(1, "Programmation en C", comments);
        entityManager.persist(book1);
        Book book2 = new Book(2, "Programmation en C", comments);
        entityManager.persist(book2);
        Book foundBook = bookRepository.findById(book2.getUuid()).get();
        assertThat(foundBook).isEqualTo(book2);
    }


    @Test
    public void should_update_book_by_id() {
        Book book1 = new Book(1, "Programmation en C", comments);
        entityManager.persist(book1);
        Book book2 = new Book(2, "Programmation en C", comments);
        entityManager.persist(book2);
        Book updatedBook = new Book(2, "Programmation en Java", comments);
        Book book = bookRepository.findById(book2.getUuid()).get();
        book.setName(updatedBook.getName());
        book.setComments(updatedBook.getComments());
        bookRepository.save(book);
        Book checkBook = bookRepository.findById(book2.getUuid()).get();

        assertThat(checkBook.getUuid()).isEqualTo(book2.getUuid());
        assertThat(checkBook.getName()).isEqualTo(updatedBook.getName());
        assertThat(checkBook.getComments()).isEqualTo(updatedBook.getComments());
    }
    @Test
    public void should_delete_book_by_id() {
        Book book1 = new Book(1, "Programmation en C", comments);
        entityManager.persist(book1);
        Book book2 = new Book(2, "Programmation en C", comments);
        entityManager.persist(book2);
        Book book3 = new Book(3, "Programmation en C", comments);
        entityManager.persist(book3);
        bookRepository.deleteById(book2.getUuid());
        Iterable<Book> books = bookRepository.findAll();
        assertThat(books).hasSize(2).contains(book1, book3);
    }


}
