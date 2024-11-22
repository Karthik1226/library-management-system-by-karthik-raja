package com.onedata.coding.repo;

import com.onedata.coding.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberRepo {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<Member> rowMapper = new BeanPropertyRowMapper<>(Member.class);

    public Member findById(int id) {
        String sql = "Select * from member where id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, rowMapper);
        }
        catch (EmptyResultDataAccessException ex){
            return null;
        }
    }
    public Member findByName(String name) {
        String sql = "Select * from member where name = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{name},rowMapper);
    }
    public List<Member> findAll(){
        String sql = "Select * from member";
        return jdbcTemplate.query(sql,rowMapper);
    }
    public int deleteById(int id){
        String sql = "Delete from member where id = ?";
        return jdbcTemplate.update(sql,id);
    }
    public int save(Member member){
        String sql = "Insert into member(name,phone,registered_date) values(?,?,?)";
        return jdbcTemplate.update(sql,member.getName(),member.getPhone(),member.getRegisteredDate());
    }
    public int update(int id, Member member){
        String sql = "update member set name = ?, phone = ?, registered_date = ? where id = ?";
        return jdbcTemplate.update(sql,member.getId(),member.getName(),member.getPhone(),member.getRegisteredDate());
    }
}
