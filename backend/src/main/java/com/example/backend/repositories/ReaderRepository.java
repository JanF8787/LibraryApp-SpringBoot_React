package com.example.backend.repositories;

import com.example.backend.models.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, Long> {

    Reader findReaderByFirstNameAndLastName(String firstName, String lastName);
}
