package com.example.backend.repositories;

import com.example.backend.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findBooksByIsBorrowedIsFalse();

    Book findBookByTitle(String title);
}
