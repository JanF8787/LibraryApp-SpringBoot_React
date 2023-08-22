package com.example.backend.controllers;

import com.example.backend.models.Book;
import com.example.backend.models.BookDto;
import com.example.backend.models.BookInfoDto;
import com.example.backend.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Book>> getAllBooks() {
        return new ResponseEntity<List<Book>>(bookService.allBooks(), HttpStatus.OK);
    }

    @GetMapping("/all_with_borrowers")
    public ResponseEntity<List<BookInfoDto>> getAllBooksWithBorrowers() {
        return new ResponseEntity<List<BookInfoDto>>(bookService.allBooksWithBorrowers(), HttpStatus.OK);
    }

    @GetMapping("/not_borrowed_books")
    public ResponseEntity<List<Book>> notBorrowedBooks() {
        return new ResponseEntity<List<Book>>(bookService.notBorrowedBooks(), HttpStatus.OK);
    }

    @PostMapping("/add_book")
    public ResponseEntity<?> addBook(@RequestBody BookDto bookDto) {
        bookService.addBook(bookDto);
        return ResponseEntity.ok().body("Book was successfully saved.");
    }

    @PatchMapping("/edit/{id}")
    public ResponseEntity<?> editBook(@RequestBody BookDto bookDto, @PathVariable Long id) {
        bookService.editBook(bookDto, id);
        return ResponseEntity.ok().body("Book was edited successfully.");
    }
}
