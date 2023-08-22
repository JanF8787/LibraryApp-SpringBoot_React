package com.example.backend.services;

import com.example.backend.models.Book;
import com.example.backend.models.BookDto;
import com.example.backend.models.BookInfoDto;

import java.util.List;

public interface BookService {

    List<Book> allBooks();

    Book addBook(BookDto bookDto);

    Book editBook(BookDto bookDto, Long id);

    List<BookInfoDto> allBooksWithBorrowers();

    List<Book> notBorrowedBooks();
}
