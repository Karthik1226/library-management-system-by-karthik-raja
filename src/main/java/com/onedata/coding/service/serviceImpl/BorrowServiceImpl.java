package com.onedata.coding.service.serviceImpl;

import com.onedata.coding.entity.Book;
import com.onedata.coding.entity.Borrow;
import com.onedata.coding.exception.BookBorrowNotAvailableException;
import com.onedata.coding.exception.MemberBorrowLimitExceedException;
import com.onedata.coding.repo.BookRepo;
import com.onedata.coding.repo.BorrowRepo;
import com.onedata.coding.service.BorrowService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BorrowServiceImpl implements BorrowService {

    @Autowired
    private BorrowRepo borrowRepo;
    @Autowired
    private BookRepo bookRepo;



    @Override
    public List<Borrow> findAllBorrowedRecord() {
        return borrowRepo.findAll();
    }

    @Override
    public String deleteBorrowedRecord(int id) {
        int rowAffected = borrowRepo.deleteById(id);
        return rowAffected > 0 ? "Record deleted" : "Record not found";
    }

    @Override
    public String updateBorrowedRecord(int id, Borrow borrow) {
        int rowAffected = borrowRepo.update(id, borrow);
        return rowAffected > 0 ? "Record updated" : "Record not found";
    }

    @Override
    public Borrow findById(int id) {
        return borrowRepo.findById(id);
    }

    @Override
    @Transactional
    public String lendBook(int book_id, int member_id) {
        int borrowLimit = 2;
        int borrowedBookCount = borrowRepo.countBookByMemberId(member_id);
        if(borrowedBookCount >= borrowLimit){
            throw new MemberBorrowLimitExceedException("Member Exceeded borrow limit");
        }

        Book book = bookRepo.findById(book_id);
        if(book .getAvailableCopies() <=0){
            throw new BookBorrowNotAvailableException("No of available copies of the book with ID " + book_id);
        }
        bookRepo.updateAvailableCopies(book_id, -1);

        Borrow borrow = new Borrow();
        borrow.setMember_id(member_id);
        borrow.setBook_id(book_id);
        borrow.setBorrowed_date(LocalDate.now());
        borrow.setDue_date(LocalDate.now().plusWeeks(2));

        borrowRepo.create(borrow);

        return "Book lent Successfully";

    }

    @Override
    @Transactional
    public String returnBook(int id) {
        Borrow borrow = borrowRepo.findById(id);
        if(borrow != null) {
            bookRepo.updateAvailableCopies(borrow.getBook_id(),1);
            borrowRepo.deleteById(id);
            return  "Book returned successfully";
        }
        return "Book record not found";
    }
}
