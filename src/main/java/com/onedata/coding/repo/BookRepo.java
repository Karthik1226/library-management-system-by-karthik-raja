package com.onedata.coding.repo;

import com.onedata.coding.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookRepo  {

    @Autowired
    public JdbcTemplate jdbcTemplate;


    private RowMapper<Book> rowMapper = new BeanPropertyRowMapper<>(Book.class);


    public Book findById(int id) {
        String sql = "Select * from Book where id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, rowMapper);
        }
        catch (EmptyResultDataAccessException ex){
            return null;
        }
    }

    public Book findByTitle(String title) {
        String sql = "Select * from Book where title = ?";
        return jdbcTemplate.queryForObject(sql,new Object[]{title},rowMapper);
    }
    public Book findByAuthor(String author) {
        String sql = "Select * from Book where author = ?";
        return jdbcTemplate.queryForObject(sql,new Object[]{author},rowMapper);
    }
    public List<Book> findAll(){
        String sql = "Select * from Book";
        return jdbcTemplate.query(sql,rowMapper);
    }
    public int deleteById(int id){
        String sql = "Delete from Book where id = ?";
        return jdbcTemplate.update(sql, id);
    }
    public int save(Book book){
        String sql = "Insert into Book (title, author, isbn, published_date, available_copies) values(?,?,?,?,?)";
        return jdbcTemplate.update(sql, book.getTitle(),book.getAuthor(),book.getIsbn(),book.getPublishedDate(),book.getAvailableCopies());
    }

    public int update(Book book){
        String sql = "Update Book set title = ?,author = ?,isbn = ?,published_date = ? , available_copies = ? where id = ?";
        return jdbcTemplate.update(sql, book.getTitle(),book.getAuthor(),book.getIsbn(),book.getPublishedDate(),book.getAvailableCopies(),book.getId());
    }

    public int updateAvailableCopies(int bookId, int count) {
        String sql = "update book set available_copies = available_copies + ? WHERE id = ?";
        return jdbcTemplate.update(sql,count,bookId);
    }
}
