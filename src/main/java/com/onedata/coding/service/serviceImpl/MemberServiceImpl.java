package com.onedata.coding.service.serviceImpl;

import com.onedata.coding.entity.Member;
import com.onedata.coding.exception.MemberNotFoundException;
import com.onedata.coding.repo.MemberRepo;
import com.onedata.coding.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepo memberRepo;
    @Override
    public Member findById(int id) {
        Member member = memberRepo.findById(id);
        if(member == null){
            throw new MemberNotFoundException("Member with id " + id + " not found");
        }
        return member;

    }

    @Override
    public Member findByName(String name) {
        return memberRepo.findByName(name);
    }

    @Override
    public List<Member> findAll() {
        return memberRepo.findAll();
    }

    @Override
    public String save(Member member) {
        int rowAffected = memberRepo.save(member);
        return rowAffected > 0 ? "Member created successfully": "Member not found";
    }

    @Override
    public String update(int id, Member member) {
        member.setId(id);
        int rowAffected = memberRepo.update(id,member);
        return rowAffected > 0 ? "Member updated successfully" : "Member not found";
    }

    @Override
    public String deleteById(int id) {
        int rowAffected = memberRepo.deleteById(id);
        return rowAffected > 0 ? "Member deleted successfully" : "Member not found";
    }
}
