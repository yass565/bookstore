package com.cloudsteam.bookstore.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Book {
    @Id
    private int uuid;
    private String name;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Comment> comments;

    public Book(int uuid, String name, List<Comment> comments) {
        this.uuid=uuid;
        this.name=name;
        this.comments = comments;
    }

    public Book() {

    }
}
