package com.onedata.coding.service;

import com.onedata.coding.entity.Member;

import java.util.List;

public interface MemberService {
    Member findById (int id);

    Member findByName (String name);

    List<Member> findAll();

    String save(Member member);

    String update(int id, Member member);

    String deleteById (int id);
}
