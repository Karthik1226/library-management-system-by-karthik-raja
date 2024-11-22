package com.onedata.coding.test.serviceTest;

import com.onedata.coding.entity.Book;
import com.onedata.coding.entity.Borrow;
import com.onedata.coding.entity.Member;
import com.onedata.coding.exception.MemberBorrowLimitExceedException;
import com.onedata.coding.repo.BookRepo;
import com.onedata.coding.repo.BorrowRepo;
import com.onedata.coding.service.serviceImpl.BookServiceImpl;
import com.onedata.coding.service.serviceImpl.BorrowServiceImpl;
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

public class BorrowServiceImplTest {
    @Mock
    private BorrowRepo borrowRepo;

    @Mock
    private BookRepo bookRepo;

    @InjectMocks
    private BorrowServiceImpl borrowServiceImpl;
    @InjectMocks
    private BookServiceImpl bookServiceImpl;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testGetAll(){
        LocalDate borrowedDate = LocalDate.of(2024,12,23);
        LocalDate dueDate = LocalDate.of(2025,1,23);
        LocalDate borrowedDate1 = LocalDate.of(2024,12,23);
        LocalDate dueDate1 = LocalDate.of(2025,1,23);
        Borrow borrow1 = new Borrow(1,2,3,borrowedDate,dueDate);
        Borrow borrow2 = new Borrow(2,4,2,borrowedDate1,dueDate1);

        List<Borrow> borrows = Arrays.asList(borrow1,borrow2);

        when(borrowRepo.findAll()).thenReturn(borrows);

        List<Borrow> result = borrowServiceImpl.findAllBorrowedRecord();

        assertEquals(2,result.size());
        assertNotNull(result);
        verify(borrowRepo,times(1)).findAll();
    }
    @Test
    void testDeleteBorrowedRecord(){
        when(borrowRepo.deleteById(1)).thenReturn(1);

        String result =borrowServiceImpl.deleteBorrowedRecord(1);
        assertEquals("Record deleted",result);
        verify(borrowRepo,times(1)).deleteById(1);
    }
    @Test
    void testDeleteBorrowedRecordNotFounr(){
        when(borrowRepo.deleteById(1)).thenReturn(0);

        String result =borrowServiceImpl.deleteBorrowedRecord(1);
        assertEquals("Record not found",result);
        verify(borrowRepo,times(1)).deleteById(1);
    }
    @Test
    void testUpdateBorrowed(){
        LocalDate borrowedDate = LocalDate.of(2024,12,23);
        LocalDate dueDate = LocalDate.of(2025,1,23);
        Borrow borrow = new Borrow(1,2,3,borrowedDate,dueDate);



        when(borrowRepo.update(borrow.getId(),borrow)).thenReturn(1);

        String result = borrowServiceImpl.updateBorrowedRecord(1,borrow);

        assertEquals("Record updated", result);
        verify(borrowRepo,times(1)).update(borrow.getId(),borrow);
    }
    @Test
    void testUpdateBookNotSaved(){
        LocalDate borrowedDate = LocalDate.of(2024,12,23);
        LocalDate dueDate = LocalDate.of(2025,1,23);
        Borrow borrow = new Borrow(1,2,3,borrowedDate,dueDate);


        when(borrowRepo.update(borrow.getId(), borrow)).thenReturn(0);

        String result = borrowServiceImpl.updateBorrowedRecord(1,borrow);

        assertEquals("Record not found", result);
        verify(borrowRepo,times(1)).update(borrow.getId(),borrow);
    }
    @Test
    void testFindById(){
        LocalDate borrowedDate = LocalDate.of(2024,12,23);
        LocalDate dueDate = LocalDate.of(2025,1,23);
        Borrow borrow = new Borrow(1,2,3,borrowedDate,dueDate);

        when(borrowRepo.findById(1)).thenReturn(borrow);

        Borrow result = borrowServiceImpl.findById(1);

        assertNotNull(result);
        assertEquals(1, borrow.getId());
        verify(borrowRepo,times(1)).findById(1);
    }
    @Test
    void testLendBook(){
        LocalDate publishedDate = LocalDate.of(2012, 12, 12);
        Book book = new Book(1,"Title","Author",2344L,publishedDate,5);

        LocalDate registeredDate = LocalDate.of(2024,9,15);
        Member member = new Member(1,"Name",9876543275L,registeredDate);

        LocalDate borrowedDate = LocalDate.of(2024,12,23);
        LocalDate dueDate = LocalDate.of(2025,1,23);
        Borrow borrow = new Borrow(1,2,3,borrowedDate,dueDate);

        when(borrowRepo.countBookByMemberId(member.getId())).thenReturn(1);
        when(bookRepo.findById(book.getId())).thenReturn(book);
        when(bookRepo.updateAvailableCopies(book.getId(), -1)).thenReturn(1);

        String result = borrowServiceImpl.lendBook(book.getId(),member.getId());

        assertNotNull(result);
        assertEquals("Book lent Successfully",result);
    }
    @Test
    void testLendLimitExceed(){
        LocalDate publishedDate = LocalDate.of(2012, 12, 12);
        Book book = new Book(1,"Title","Author",2344L,publishedDate,5);

        LocalDate registeredDate = LocalDate.of(2024,9,15);
        Member member = new Member(1,"Name",9876543275L,registeredDate);

        when(borrowRepo.countBookByMemberId(member.getId())).thenReturn(2);

        MemberBorrowLimitExceedException ex = assertThrows(MemberBorrowLimitExceedException.class, () ->{
            borrowServiceImpl.lendBook(book.getId(),member.getId());
        });

        assertEquals("Member Exceeded borrow limit",ex.getMessage());
    }
    @Test
    void testReturnBook(){
        LocalDate borrowedDate = LocalDate.of(2024,12,23);
        LocalDate dueDate = LocalDate.of(2025,1,23);
        Borrow borrow = new Borrow(1,2,3,borrowedDate,dueDate);

        LocalDate publishedDate = LocalDate.of(2012, 12, 12);
        Book book = new Book(1,"Title","Author",2344L,publishedDate,5);

        when(borrowRepo.findById(1)).thenReturn(borrow);
        when(bookRepo.updateAvailableCopies(3, 1)).thenReturn(1);

        String result = borrowServiceImpl.returnBook(1);

        assertEquals("Book returned successfully",result);
        verify(borrowRepo,times(1)).findById(1);
        verify(bookRepo,times(1)).updateAvailableCopies(3, 1);
    }
    @Test
    void testReturnBookNotAvailable(){
        LocalDate borrowedDate = LocalDate.of(2024,12,23);
        LocalDate dueDate = LocalDate.of(2025,1,23);
        Borrow borrow = new Borrow(1,2,3,borrowedDate,dueDate);

        when(borrowRepo.findById(1)).thenReturn(null);

        String result = borrowServiceImpl.returnBook(1);

        assertEquals("Book record not found",result);
        verify(borrowRepo,times(0)).deleteById(1);
    }
}
