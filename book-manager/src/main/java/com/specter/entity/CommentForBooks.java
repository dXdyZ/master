package com.specter.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class CommentForBooks {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Books books;

    private String comment;

}
