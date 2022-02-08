package com.cloudsteam.bookstore.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.concurrent.CompletableFuture;

@Entity
@Data
@NoArgsConstructor
public class Comment {
    @Id
    private int id;
    private String comment;

    public Comment(int id, String comment) {
        this.id=id;
        this.comment = comment;
    }


}
