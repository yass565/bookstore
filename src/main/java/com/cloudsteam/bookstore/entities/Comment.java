package com.cloudsteam.bookstore.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Comment {
    @Id
    private int id;
    private String comment;
}
