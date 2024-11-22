package com.onedata.coding.test.controllerTest;


import com.onedata.coding.entity.Book;
import com.onedata.coding.service.serviceImpl.BookServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookServiceImpl bookServiceImpl;


    @Test
    void testGetBookById() throws Exception {
        Book book = new Book(1, "Title", "Author", 2344L,LocalDate.of(2014,1,2), 5);
        when(bookServiceImpl.findById(1)).thenReturn(book);

        mockMvc.perform(get("/v1/api/books/id/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(book.getId()))
                .andExpect(jsonPath("$.title").value(book.getTitle()))
                .andExpect(jsonPath("$.author").value(book.getAuthor()))
                .andExpect(jsonPath("$.isbn").value(book.getIsbn()))
                .andExpect(jsonPath("$.publishedDate").value(book.getPublishedDate().toString()))
                .andExpect(jsonPath("$.availableCopies").value(book.getAvailableCopies()));
    }
    @Test
    void testGetBookByTitle() throws Exception {
        Book book = new Book(1, "Title", "Author", 2344L, LocalDate.now(), 5);
        when(bookServiceImpl.findByTitle("Title")).thenReturn(book);

        mockMvc.perform(get("/v1/api/books/title/{title}", "Title")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(book.getId()))
                .andExpect(jsonPath("$.title").value(book.getTitle()))
                .andExpect(jsonPath("$.author").value(book.getAuthor()))
                .andExpect(jsonPath("$.isbn").value(book.getIsbn()))
                .andExpect(jsonPath("$.publishedDate").value(book.getPublishedDate().toString()))
                .andExpect(jsonPath("$.availableCopies").value(book.getAvailableCopies()));
    }
    @Test
    void testGetBookByAuthor() throws Exception {
        Book book = new Book(1, "Title", "Author", 2344L, LocalDate.now(), 5);
        when(bookServiceImpl.findByAuthor("Author")).thenReturn(book);

        mockMvc.perform(get("/v1/api/books/author/{author}", "Author")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(book.getId()))
                .andExpect(jsonPath("$.title").value(book.getTitle()))
                .andExpect(jsonPath("$.author").value(book.getAuthor()))
                .andExpect(jsonPath("$.isbn").value(book.getIsbn()))
                .andExpect(jsonPath("$.publishedDate").value(book.getPublishedDate().toString()))
                .andExpect(jsonPath("$.availableCopies").value(book.getAvailableCopies()));
    }
    @Test
    void testDeleteBook() throws Exception {
        Book book = new Book(1, "Title", "Author", 2344L, LocalDate.now(), 5);
        when(bookServiceImpl.deleteById(1)).thenReturn("Book with ID " + 1 + " deleted successfully.");

        mockMvc.perform(delete("/v1/api/books/{id}",1)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string("Book with ID " + 1 + " deleted successfully."));
    }
    /*@Test
    void testCreateBook() throws JsonProcessingException {
        Book book = new Book(1, "Title", "Author", 2344L, LocalDate.now(), 5);
        when(bookServiceImpl.save(book)).thenReturn("Book created successfully with ID: " + book.getId());
    }*/
}
