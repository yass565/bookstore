package com.cloudsteam.bookstore.repositories;

import com.cloudsteam.bookstore.entities.Book;
import com.cloudsteam.bookstore.entities.Comment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BookRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void should_save_a_book() {

        //Given
        List<Comment> comments = List.of(new Comment(1, "Livre très interessant"), new Comment(2, "Riche en contenu"));

        //When
        var book = bookRepository.save(new Book(1, "Programmation en C", comments));
        var expected=new Book(1, "Programmation en C", comments);

        //Then
        assertThat(book).isEqualTo(expected);
    }

    @Test
    void should_find_all_books() {

        //Given
        var commentsList1 = List.of(new Comment(1, "Livre très intéressant"), new Comment(2, "Riche en contenu"));
        var book1 = new Book(1, "Programmation en C", commentsList1);
        entityManager.persist(book1);

        var commentsList2 = List.of(new Comment(3, "Livre très intéressant"), new Comment(4, "Riche en contenu"));
        var book2 = new Book(2, "Programmation en C", commentsList2);
        entityManager.persist(book2);

        var commentsList3 = List.of(new Comment(5, "Livre très intéressant"), new Comment(6, "Riche en contenu"));
        var book3 = new Book(3, "Programmation en C", commentsList3);
        entityManager.persist(book3);

        //When
        var books = bookRepository.findAll();

        //Then
        assertThat(books).hasSize(3).contains(book1, book2, book3);
    }

    @Test
    public void should_find_book_by_id() {
        //Given
        var commentsList1 = List.of(new Comment(1, "Livre très intéressant"), new Comment(2, "Riche en contenu"));
        var book1 = new Book(1, "Programmation en C", commentsList1);
        entityManager.persist(book1);

        var commentsList2 = List.of(new Comment(3, "Livre très intéressant"), new Comment(4, "Riche en contenu"));

        var book2 = new Book(2, "Programmation en C", commentsList2);
        entityManager.persist(book2);

        //When
        var foundBook = bookRepository.findById(book2.getUuid()).get();

        //Then
        assertThat(foundBook).isEqualTo(book2);
    }


    @Test
    void should_update_book_by_id() {

        //Given
        var comments = List.of(new Comment(1, "Livre très interessant"), new Comment(2, "Riche en contenu"));
        var book = new Book(1, "Programmation en C", comments);

        //When
        book.setName("Programmation en Java");
        bookRepository.save(book);
        var actual = bookRepository.findById(book.getUuid()).get();

        //Then
        assertThat(actual).isEqualTo(book);
    }

    @Test
    void should_delete_book_by_id() {

        //Given
        var commentsList1 = List.of(new Comment(1, "Livre très intéressant"), new Comment(2, "Riche en contenu"));
        var book1 = new Book(1, "Programmation en C", commentsList1);
        entityManager.persist(book1);

        //When
        bookRepository.deleteById(book1.getUuid());
        var books = bookRepository.findAll();

        //Then
        assertThat(books).isEmpty();
    }


}
