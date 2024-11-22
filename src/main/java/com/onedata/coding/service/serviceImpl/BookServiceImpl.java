package com.onedata.coding.service.serviceImpl;

import com.onedata.coding.entity.Book;
import com.onedata.coding.exception.BookNotFoundException;
import com.onedata.coding.repo.BookRepo;
import com.onedata.coding.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepo bookRepo;
    @Override
    public Book findById(int id) {
        Book book = bookRepo.findById(id);
        if(book == null) {
            throw new BookNotFoundException("Book with ID " + id + " not found");
        }
        return book;
    }

    @Override
    public Book findByTitle(String title) {
        return bookRepo.findByTitle(title);
    }

    @Override
    public Book findByAuthor(String author) {
        return bookRepo.findByAuthor(author);
    }

    @Override
    public List<Book> findAll() {
        return bookRepo.findAll();
    }

    @Override
    public String deleteById(int id) {
        int rowAffected = bookRepo.deleteById(id);
        return rowAffected > 0 ? "Book deleted successfully" : "Book not found";
    }

    @Override
    public String save(Book book) {
        int rowAffected = bookRepo.save(book);
        return rowAffected > 0 ? "Book saved successfully" : "Book not created";
    }

    @Override
    public String update(int id,Book book) {
        book.setId(id);
        int rowAffected = bookRepo.update(book);
        return rowAffected > 0 ? "Book updated successfully" : "Book not updated";
    }
}
