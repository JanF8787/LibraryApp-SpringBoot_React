package com.example.backend.controllers;

import com.example.backend.models.Loan;
import com.example.backend.services.LoanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin
@RequestMapping("/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Loan>> getAllLoans() {
        return new ResponseEntity<List<Loan>>(loanService.allLoans(), HttpStatus.OK);
    }

    @PostMapping("/add/{bookId}/{readerId}")
    public ResponseEntity<?> addLoan(@PathVariable Long bookId, @PathVariable Long readerId) {
        return new ResponseEntity<>(loanService.addLoan(bookId, readerId), HttpStatus.OK);
    }

    @PatchMapping("return/{id}")
    public ResponseEntity<?> returnBook(@PathVariable Long id) {
        return new ResponseEntity<>(loanService.returnBook(id), HttpStatus.OK);
    }
}
