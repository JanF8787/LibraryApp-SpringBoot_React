package com.example.backend.services;

import com.example.backend.models.*;
import com.example.backend.repositories.BookRepository;
import com.example.backend.repositories.LoanRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DatabaseBookService implements BookService {

    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;

    public DatabaseBookService(BookRepository bookRepository, LoanRepository loanRepository) {
        this.bookRepository = bookRepository;
        this.loanRepository = loanRepository;
    }

    @Override
    public List<Book> allBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book addBook(BookDto bookDto) {
        Book book = new Book();

        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setBorrowed(false);

        bookRepository.save(book);

        return book;
    }

    @Override
    public Book editBook(BookDto bookDto, Long id) {
        Optional<Book> book = bookRepository.findById(id);

        book.get().setTitle(bookDto.getTitle());
        book.get().setAuthor(bookDto.getAuthor());

        bookRepository.save(book.get());

        return book.get();
    }

    @Override
    public List<BookInfoDto> allBooksWithBorrowers() {
        List<Book> books = bookRepository.findAll();
        List<Loan> loans = loanRepository.findAll();

        List<BookInfoDto> booksWithBorrowers = new ArrayList<>();

        for (Book book : books) {
            BookInfoDto bookInfoDto = new BookInfoDto();

            bookInfoDto.setId(book.getId());
            bookInfoDto.setTitle(book.getTitle());
            bookInfoDto.setAuthor(book.getAuthor());
            bookInfoDto.setBorrowed(book.getBorrowed());

            for (Loan loan : loans) {
                if (loan.getReturnDate() == null && loan.getBook().getId() == book.getId()) {
                    bookInfoDto.setBorrower(loan.getReader().getFirstName() + " " + loan.getReader().getLastName());
                }
            }

            booksWithBorrowers.add(bookInfoDto);
        }
        return booksWithBorrowers;
    }

    @Override
    public List<Book> notBorrowedBooks() {
        return bookRepository.findBooksByIsBorrowedIsFalse();
    }
}
