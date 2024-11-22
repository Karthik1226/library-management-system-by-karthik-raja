package com.onedata.coding.service;

import com.onedata.coding.entity.Book;

import java.util.List;

public interface BookService {

    Book findById(int id);
    Book findByTitle(String title);
    Book findByAuthor(String author);
    List<Book> findAll();

    String deleteById(int id);

    String save(Book book);

    String update(int id, Book book);

}
