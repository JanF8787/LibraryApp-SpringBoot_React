package com.example.backend.services;

import com.example.backend.models.Reader;
import com.example.backend.models.ReaderDto;

import java.util.List;

public interface ReaderService {

    List<Reader> allReaders();

    Reader addReader(ReaderDto readerDto);

    Reader editReader(ReaderDto readerDto, Long id);

}
