package com.onedata.coding.repo;

import com.onedata.coding.entity.Borrow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BorrowRepo {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<Borrow> rowMapper = new BeanPropertyRowMapper<>(Borrow.class);

    public List<Borrow> findAll(){
        String sql = "Select * from borrow";
        return jdbcTemplate.query(sql, rowMapper);
    }
    public Borrow findById(int id){
        String sql = "select * from borrow where id = ? ";
        return jdbcTemplate.queryForObject(sql,new Object[]{id},rowMapper);
    }
    public int create(Borrow borrow){
        String sql = "insert into borrow (member_id,book_id,borrowed_date,due_date) values(?,?,?,?)";
        return jdbcTemplate.update(sql,borrow.getMember_id(),borrow.getBook_id(),borrow.getBorrowed_date(),borrow.getDue_date());
    }
    public int update(int id,Borrow borrow){
        borrow.setId(id);
        String sql = "update borrow set member_id = ?,book_id = ?,borrowed_date = ?,due_date = ? where id = ?";
        return jdbcTemplate.update(sql,borrow.getMember_id(),borrow.getBook_id(),borrow.getBorrowed_date(),borrow.getDue_date(),borrow.getId());
    }
    public int deleteById(int id){
        String sql = "delete from borrow where id = ? ";
        return jdbcTemplate.update(sql,id);
    }
    public Borrow getByBookAndMember(int book_id,int member_id){
        String sql = "select * from borrow where book_id = ? and member_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[] {book_id,member_id},rowMapper);
    }
    public int countBookByMemberId(int member_id) {
        String sql = "select count(*) from borrow where member_id = ?" ;
        return jdbcTemplate.queryForObject(sql, Integer.class,member_id);
    }
}
