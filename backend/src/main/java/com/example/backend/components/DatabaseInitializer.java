package com.example.backend.components;

import com.example.backend.models.Book;
import com.example.backend.models.Loan;
import com.example.backend.models.Reader;
import com.example.backend.repositories.BookRepository;
import com.example.backend.repositories.LoanRepository;
import com.example.backend.repositories.ReaderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final BookRepository bookRepository;
    private final ReaderRepository readerRepository;
    private final LoanRepository loanRepository;

    public DatabaseInitializer(BookRepository bookRepository, ReaderRepository readerRepository, LoanRepository loanRepository) {
        this.bookRepository = bookRepository;
        this.readerRepository = readerRepository;
        this.loanRepository = loanRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if(bookRepository.count() == 0 && readerRepository.count() == 0 && loanRepository.count() == 0) {
            Book book1 = new Book(1L, "Marina", "Andrej Sladkovic", false);
            bookRepository.save(book1);
            Book book2 = new Book(2L, "Krvave sonety", "Pavol Orszsagh-Hviezdoslav", true);
            bookRepository.save(book2);
            Book book3 = new Book(3L, "Jozef Mak", "Jozef ciger Hronsky", true);
            bookRepository.save(book3);

            Reader reader1 = new Reader(1L, "MN123456", "Peter", "Petrovsky", LocalDate.of(1990, 7, 12));
            readerRepository.save(reader1);
            Reader reader2 = new Reader(2L, "MN654321", "Andrej", "Andrejovsky", LocalDate.of(1989, 3, 20));
            readerRepository.save(reader2);
            Reader reader3 = new Reader(3L, "MN567890", "Jakub", "Jakubovsky", LocalDate.of(1997, 8, 30));
            readerRepository.save(reader3);

            Loan loan1 = new Loan(1L, book1, reader1, LocalDate.of(2023, 7, 20), LocalDate.of(2023, 7, 27));
            loanRepository.save(loan1);
            Loan loan2 = new Loan(2L, book2, reader2, LocalDate.of(2023, 8, 17), null);
            loanRepository.save(loan2);
            Loan loan3 = new Loan(3L, book3, reader3, LocalDate.of(2023, 8, 15), null);
            loanRepository.save(loan3);
        }

    }
}
