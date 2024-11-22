package com.onedata.coding.controller;

import com.onedata.coding.entity.Book;
import com.onedata.coding.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/api/books")
public class BookController {
    @Autowired
    public BookService bookService;

    @GetMapping("/id/{id}")
    public Book getBookById(@PathVariable int id){
        return bookService.findById(id);
    }
    @GetMapping("/title/{title}")
    public Book getBookByTitle(@PathVariable String title){
        return bookService.findByTitle(title);
    }
    @GetMapping("/author/{author}")
    public Book getBookByAuthor(@PathVariable String author){
        return bookService.findByAuthor(author);
    }
    @GetMapping()
    public List<Book> getAllBooks(){
        return bookService.findAll();
    }
    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable int id){
        return bookService.deleteById(id);
    }
    @PostMapping()
    public String createBook(@RequestBody Book book){
        return bookService.save(book);
    }
    @PutMapping("/{id}")
    public String updateBook(@PathVariable int id ,@RequestBody Book book){
        return bookService.update(id, book);
    }
}
