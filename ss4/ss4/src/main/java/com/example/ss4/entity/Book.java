package com.example.ss4.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String publisher;

    @Column(nullable = false)
    private Integer year;

    // Constructors
    public Book() {}

    public Book(String title, String author, String publisher, Integer year) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.year = year;
    }
}