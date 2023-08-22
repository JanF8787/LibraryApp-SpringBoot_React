package com.example.backend.services;

import com.example.backend.models.Loan;

import java.util.List;

public interface LoanService {

    List<Loan> allLoans();

    Loan addLoan(Long bookId, Long readerId);

    Loan returnBook(Long id);
}
