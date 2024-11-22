package com.onedata.coding.controller;

import com.onedata.coding.entity.Member;
import com.onedata.coding.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/api/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @GetMapping("/id/{id}")
    public Member findById(@PathVariable int id) {
        return memberService.findById(id);
    }
    @GetMapping("/name/{name}")
    public Member findByName(@PathVariable String name) {
        return memberService.findByName(name);
    }
    @GetMapping()
    public List<Member> findAll() {
        return memberService.findAll();
    }
    @PostMapping()
    public String save(@RequestBody Member member){
        return memberService.save(member);
    }
    @PutMapping("/{id}")
    public String updateMember(@PathVariable int id,@RequestBody Member member){
        return memberService.update(id,member);
    }
    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable int id){
        return memberService.deleteById(id);
    }
}
