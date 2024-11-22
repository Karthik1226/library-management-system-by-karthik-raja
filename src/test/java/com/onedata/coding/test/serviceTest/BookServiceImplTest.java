package com.onedata.coding.test.serviceTest;

import com.onedata.coding.exception.BookNotFoundException;

import java.util.Arrays;
import java.util.List;
import com.onedata.coding.entity.Book;
import com.onedata.coding.repo.BookRepo;
import com.onedata.coding.service.serviceImpl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class BookServiceImplTest {

    @Mock
    private BookRepo bookRepo;
    @InjectMocks
    private BookServiceImpl bookServiceImpl;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testGetById(){
        LocalDate publishedDate = LocalDate.of(2012, 12, 12);
        Book book = new Book(1,"Title","Author",2344L,publishedDate,5);

        when(bookRepo.findById(1)).thenReturn(book);
        Book result = bookServiceImpl.findById(1);
       assertNotNull(result);
       assertEquals(1, book.getId());
       verify(bookRepo,times(1)).findById(1);
    }
    @Test
    void testGetIdNotFound(){
        when(bookRepo.findById(1)).thenReturn(null);

        assertThrows(BookNotFoundException.class, ()-> bookServiceImpl.findById(1));
        verify(bookRepo,times(1)).findById(1);
    }
    @Test
    void testGetByTitle(){
        LocalDate publishedDate = LocalDate.of(2012, 12, 12);
        Book book = new Book(1,"Title","Author",2344L,publishedDate,5);

        when(bookRepo.findByTitle("Title")).thenReturn(book);

        Book result = bookServiceImpl.findByTitle("Title");
        assertNotNull(result);
        assertEquals("Title",result.getTitle());
        verify(bookRepo,times(1)).findByTitle("Title");
    }
    @Test
    void testGetAll(){
        LocalDate publishedDate1 = LocalDate.of(2012, 12, 12);
        LocalDate publishedDate2 = LocalDate.of(2016, 4, 29);
        Book book1 = new Book(1,"Title","Author",2344L,publishedDate1,5);
        Book book2 = new Book(2,"Title1","Author1",2424L,publishedDate2,10);
        List<Book> books = Arrays.asList(book1, book2);

        when(bookRepo.findAll()).thenReturn(books);

        List<Book> result = bookServiceImpl.findAll();

        assertNotNull(result);
        assertEquals(2,result.size());
        verify(bookRepo,times(1)).findAll();
    }
    @Test
    void testDeleteBook(){
        when(bookRepo.deleteById(1)).thenReturn(1);

            String result = bookServiceImpl.deleteById(1);

            assertEquals("Book deleted successfully", result);
            verify(bookRepo,times(1)).deleteById(1);
    }
    @Test
    void testDeleteBookIdNotFound(){
        when(bookRepo.deleteById(1)).thenReturn(0);

        String result = bookServiceImpl.deleteById(1);

        assertEquals("Book not found", result);
        verify(bookRepo,times(1)).deleteById(1);
    }
    @Test
    void testCreateBook(){
        LocalDate publishedDate = LocalDate.of(2012, 12, 12);
        Book book = new Book(1,"Title","Author",2344L,publishedDate,5);

        when(bookRepo.save(book)).thenReturn(1);

        String result = bookServiceImpl.save(book);

        assertEquals("Book saved successfully", result);
        verify(bookRepo,times(1)).save(book);
    }
    @Test
    void testCreateBookNotSaved(){
        LocalDate publishedDate = LocalDate.of(2012, 12, 12);
        Book book = new Book(1,"Title","Author",2344L,publishedDate,5);

        when(bookRepo.save(book)).thenReturn(0);

        String result = bookServiceImpl.save(book);

        assertEquals("Book not created", result);
        verify(bookRepo,times(1)).save(book);
    }
    @Test
    void testUpdateBook(){
        LocalDate publishedDate = LocalDate.of(2012, 12, 12);
        Book book = new Book(1,"Title","Author",2344L,publishedDate,5);

        when(bookRepo.update(book)).thenReturn(1);

        String result = bookServiceImpl.update(1,book);

        assertEquals("Book updated successfully", result);
        verify(bookRepo,times(1)).update(book);
    }
    @Test
    void testUpdateBookNotSaved(){
        LocalDate publishedDate = LocalDate.of(2012, 12, 12);
        Book book = new Book(1,"Title","Author",2344L,publishedDate,5);

        when(bookRepo.update(book)).thenReturn(0);

        String result = bookServiceImpl.update(1,book);

        assertEquals("Book not updated", result);
        verify(bookRepo,times(1)).update(book);
    }

}