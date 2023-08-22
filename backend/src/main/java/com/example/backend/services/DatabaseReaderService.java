package com.example.backend.services;

import com.example.backend.models.Reader;
import com.example.backend.models.ReaderDto;
import com.example.backend.repositories.ReaderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DatabaseReaderService implements ReaderService {

    private final ReaderRepository readerRepository;

    public DatabaseReaderService(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    @Override
    public List<Reader> allReaders() {
        return readerRepository.findAll();
    }

    @Override
    public Reader addReader(ReaderDto readerDto) {
        Reader reader = new Reader();

        reader.setIdCard(readerDto.getIdCard());
        reader.setFirstName(readerDto.getFirstName());
        reader.setLastName(readerDto.getLastName());
        reader.setDateOfBirth(readerDto.getDateOfBirth());

        readerRepository.save(reader);

        return reader;
    }

    @Override
    public Reader editReader(ReaderDto readerDto, Long id) {
        Optional<Reader> reader = readerRepository.findById(id);

        reader.get().setIdCard(readerDto.getIdCard());
        reader.get().setFirstName(readerDto.getFirstName());
        reader.get().setLastName(readerDto.getLastName());
        reader.get().setDateOfBirth(readerDto.getDateOfBirth());

        readerRepository.save(reader.get());

        return reader.get();
    }
}
