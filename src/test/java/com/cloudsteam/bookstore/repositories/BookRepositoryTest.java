package com.cloudsteam.bookstore.repositories;

import com.cloudsteam.bookstore.entities.Book;
import com.cloudsteam.bookstore.entities.Comment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BookRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BookRepository bookRepository;





    @Test
    public void should_save_a_book() {
        List<Comment> comments = new ArrayList<Comment>();
        comments.add(new Comment(1, "Livre très interessant"));
        comments.add(new Comment(2, "Riche en contenu"));

        Book book = bookRepository.save(new Book(1, "Programmation en C", comments));
        Book expected=new Book(1, "Programmation en C", comments);

        assertThat(book).isEqualTo(expected);
    }

    @Test
    public void should_find_all_books() {
        List<Comment> commentsList1 = new ArrayList<Comment>();
        commentsList1.add(new Comment(1, "Livre très interessant"));
        commentsList1.add(new Comment(2, "Riche en contenu"));
        Book book1 = new Book(1, "Programmation en C", commentsList1);
        entityManager.persist(book1);

        List<Comment> commentsList2 = new ArrayList<Comment>();
        commentsList2.add(new Comment(3, "Livre très interessant"));
        commentsList2.add(new Comment(4, "Riche en contenu"));

        Book book2 = new Book(2, "Programmation en C", commentsList2);
        entityManager.persist(book2);

        List<Comment> commentsList3 = new ArrayList<Comment>();
        commentsList3.add(new Comment(5, "Livre très interessant"));
        commentsList3.add(new Comment(6, "Riche en contenu"));

        Book book3 = new Book(3, "Programmation en C", commentsList3);
        entityManager.persist(book3);

        Iterable<Book> books = bookRepository.findAll();
        assertThat(books).hasSize(3).contains(book1, book2, book3);
    }

    @Test
    public void should_find_book_by_id() {
        List<Comment> commentsList1 = new ArrayList<Comment>();
        commentsList1.add(new Comment(1, "Livre très interessant"));
        commentsList1.add(new Comment(2, "Riche en contenu"));

        Book book1 = new Book(1, "Programmation en C", commentsList1);
        entityManager.persist(book1);

        List<Comment> commentsList2 = new ArrayList<Comment>();
        commentsList2.add(new Comment(3, "Livre très interessant"));
        commentsList2.add(new Comment(4, "Riche en contenu"));

        Book book2 = new Book(2, "Programmation en C", commentsList2);
        entityManager.persist(book2);

        Book foundBook = bookRepository.findById(book2.getUuid()).get();
        assertThat(foundBook).isEqualTo(book2);
    }


    @Test
    public void should_update_book_by_id() {
        List<Comment> commentsList1 = new ArrayList<Comment>();
        commentsList1.add(new Comment(1, "Livre très interessant"));
        commentsList1.add(new Comment(2, "Riche en contenu"));

        Book book1 = new Book(1, "Programmation en C", commentsList1);
        entityManager.persist(book1);

        List<Comment> commentsList2 = new ArrayList<Comment>();
        commentsList2.add(new Comment(3, "Livre très interessant"));
        commentsList2.add(new Comment(4, "Riche en contenu"));

        Book book2 = new Book(2, "Programmation en C", commentsList2);
        entityManager.persist(book2);

        Book updatedBook = new Book(2, "Programmation en Java", commentsList2);
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
        List<Comment> commentsList1 = new ArrayList<Comment>();
        commentsList1.add(new Comment(1, "Livre très interessant"));
        commentsList1.add(new Comment(2, "Riche en contenu"));

        Book book1 = new Book(1, "Programmation en C", commentsList1);
        entityManager.persist(book1);
        entityManager.persist(book1);
        bookRepository.deleteById(book1.getUuid());
        Iterable<Book> books = bookRepository.findAll();
        assertThat(books).hasSize(0);
    }


}
