package com.onedata.coding.test.serviceTest;

import com.onedata.coding.entity.Member;
import com.onedata.coding.exception.MemberNotFoundException;
import com.onedata.coding.repo.MemberRepo;
import com.onedata.coding.service.serviceImpl.MemberServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MemberServiceImplTest {
    @Mock
    private MemberRepo memberRepo;

    @InjectMocks
    private MemberServiceImpl memberServiceImpl;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testGetMemberById(){
        LocalDate registeredDate = LocalDate.of(2024,9,15);
        Member member = new Member(1,"Name",9876543275L,registeredDate);

        when(memberRepo.findById(1)).thenReturn(member);

        Member result = memberServiceImpl.findById(1);
        assertNotNull(result);
        assertEquals(1, member.getId());
        verify(memberRepo,times(1)).findById(1);
    }
    @Test
    void testGetIdNotFound(){
        when(memberRepo.findById(1)).thenReturn(null);

        assertThrows(MemberNotFoundException.class, ()-> memberServiceImpl.findById(1));
        verify(memberRepo,times(1)).findById(1);
    }
    @Test
    void testGetByName(){
        LocalDate registeredDate = LocalDate.of(2024,9,15);
        Member member = new Member(1,"Name",9876543275L,registeredDate);


        when(memberRepo.findByName("Name")).thenReturn(member);

        Member result = memberServiceImpl.findByName("Name");
        assertNotNull(result);
        assertEquals("Name",result.getName());
        verify(memberRepo,times(1)).findByName("Name");
    }
    @Test
    void testGetAll(){
        LocalDate registeredDate1 = LocalDate.of(2024,9,15);
        LocalDate registeredDate2 = LocalDate.of(2024,10,25);
        Member member1 = new Member(1,"Name",9876543275L,registeredDate1);
        Member member2 = new Member(1,"Name",9876543275L,registeredDate2);
        List<Member> members = Arrays.asList(member1,member2);

        when(memberRepo.findAll()).thenReturn(members);

        List<Member> result = memberServiceImpl.findAll();
        assertNotNull(result);
        assertEquals(2,result.size());
        verify(memberRepo,times(1)).findAll();
    }
    @Test
    void testDeleteMember(){
        when(memberRepo.deleteById(1)).thenReturn(1);

        String result = memberServiceImpl.deleteById(1);

        assertEquals("Member deleted successfully", result);
        verify(memberRepo,times(1)).deleteById(1);
    }
    @Test
    void testDeleteMemberIdNotFound(){
        when(memberRepo.deleteById(1)).thenReturn(0);

        String result = memberServiceImpl.deleteById(1);

        assertEquals("Member not found", result);
        verify(memberRepo,times(1)).deleteById(1);
    }
    @Test
    void testCreateMember(){
        LocalDate registeredDate = LocalDate.of(2024,9,15);
        Member member = new Member(1,"Name",9876543275L,registeredDate);

        when(memberRepo.save(member)).thenReturn(1);

        String result = memberServiceImpl.save(member);

        assertEquals("Member created successfully", result);
        verify(memberRepo,times(1)).save(member);
    }
    @Test
    void testCreateMemberNotSaved(){
        LocalDate registeredDate = LocalDate.of(2024,9,15);
        Member member = new Member(1,"Name",9876543275L,registeredDate);

        when(memberRepo.save(member)).thenReturn(0);

        String result = memberServiceImpl.save(member);

        assertEquals("Member not found", result);
        verify(memberRepo,times(1)).save(member);
    }
    @Test
    void testUpdateBook(){
        LocalDate registeredDate = LocalDate.of(2024,9,15);
        Member member = new Member(1,"Name",9876543275L,registeredDate);


        when(memberRepo.update(member.getId(),member)).thenReturn(1);

        String result = memberServiceImpl.update(1,member);

        assertEquals("Member updated successfully", result);
        verify(memberRepo,times(1)).update(member.getId(),member);
    }
    @Test
    void testUpdateBookNotSaved(){
        LocalDate registeredDate = LocalDate.of(2024,9,15);
        Member member = new Member(1,"Name",9876543275L,registeredDate);


        when(memberRepo.update(member.getId(), member)).thenReturn(0);

        String result = memberServiceImpl.update(1,member);

        assertEquals("Member not found", result);
        verify(memberRepo,times(1)).update(member.getId(),member);
    }

}

