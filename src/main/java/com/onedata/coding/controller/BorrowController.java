package com.onedata.coding.controller;

import com.onedata.coding.entity.Borrow;
import com.onedata.coding.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/api/borrow")
public class BorrowController {
    @Autowired
    private BorrowService borrowService;

    @PostMapping("/lend")
    public String lendBook(@RequestParam int book_id,@RequestParam int member_id) {
        return borrowService.lendBook(book_id, member_id);
    }
    @PostMapping("/return")
    public String returnBook(@RequestParam int id) {
        return borrowService.returnBook(id);
    }
    @GetMapping()
    public List<Borrow> findAllBorrowedRecord(){
        return borrowService.findAllBorrowedRecord();
    }
    @GetMapping("/id/{id}")
    public Borrow findById(@PathVariable int id){
        return borrowService.findById(id);
    }
    @PutMapping("/{id}")
    public String updateBorrowedRecord(@PathVariable int id ,@RequestBody Borrow borrow){
        return borrowService.updateBorrowedRecord(id,borrow);
    }
    @DeleteMapping("/{id}")
    public String deleteBorrowedRecord(@PathVariable int id){
        return borrowService.deleteBorrowedRecord(id);
    }
}
