package com.cloudsteam.bookstore.repositories;

import com.cloudsteam.bookstore.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
