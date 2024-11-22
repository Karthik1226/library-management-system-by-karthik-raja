package com.onedata.coding.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String author;
    private Long isbn;
    @Column(name = "published_date")
    private LocalDate publishedDate;
    @Column(name = "available_copies")
    private int availableCopies;

}
