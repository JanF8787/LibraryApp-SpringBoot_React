package com.example.backend.services;

import com.example.backend.models.Book;
import com.example.backend.models.Loan;
import com.example.backend.models.Reader;
import com.example.backend.repositories.BookRepository;
import com.example.backend.repositories.LoanRepository;
import com.example.backend.repositories.ReaderRepository;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DatabaseLoanService implements LoanService{

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final ReaderRepository readerRepository;

    public DatabaseLoanService(LoanRepository loanRepository, BookRepository bookRepository, ReaderRepository readerRepository) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.readerRepository = readerRepository;
    }

    @Override
    public List<Loan> allLoans() {
        return loanRepository.findAll();
    }

    @Override
    public Loan addLoan(Long bookId, Long readerId) {
        Optional<Book> book = bookRepository.findById(bookId);
        Optional<Reader> reader = readerRepository.findById(readerId);

        Loan loan = new Loan();

        loan.setBook(book.get());
        loan.setReader(reader.get());
        loan.setBorrowDate(LocalDate.now());

        book.get().setBorrowed(true);
        bookRepository.save(book.get());

        loanRepository.save(loan);
        return loan;
    }

    @Override
    public Loan returnBook(Long id) {
        Optional<Loan> loan = loanRepository.findById(id);
        Optional<Book> book = bookRepository.findById((loan.get().getBook().getId()));

        loan.get().setReturnDate(LocalDate.now());
        loanRepository.save(loan.get());

        book.get().setBorrowed(false);
        bookRepository.save(book.get());

        return loan.get();
    }
}
