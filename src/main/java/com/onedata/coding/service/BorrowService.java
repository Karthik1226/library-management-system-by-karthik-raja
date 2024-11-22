package com.onedata.coding.service;

import com.onedata.coding.entity.Borrow;

import java.util.List;

public interface BorrowService {
    List<Borrow> findAllBorrowedRecord();
    String deleteBorrowedRecord(int id);
    String updateBorrowedRecord(int id, Borrow borrow);
    Borrow findById(int id);
    String lendBook(int book_id,int member_id);
    String returnBook(int id);



}
