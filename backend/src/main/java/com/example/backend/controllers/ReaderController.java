package com.example.backend.controllers;

import com.example.backend.models.Reader;
import com.example.backend.models.ReaderDto;
import com.example.backend.services.ReaderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin
@RequestMapping("/readers")
public class ReaderController {

    private final ReaderService readerService;

    public ReaderController(ReaderService readerService) {
        this.readerService = readerService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Reader>> getAllReaders() {
        return new ResponseEntity<List<Reader>>(readerService.allReaders(), HttpStatus.OK);
    }

    @PostMapping("/add_reader")
    public ResponseEntity<?> addReader(@RequestBody ReaderDto readerDto) {
        readerService.addReader(readerDto);
        return ResponseEntity.ok().body("Reader was successfully saved.");
    }

    @PatchMapping("/edit/{id}")
    public ResponseEntity<?> editReader(@RequestBody ReaderDto readerDto, @PathVariable Long id) {
        readerService.editReader(readerDto, id);
        return ResponseEntity.ok().body("Reader was successfully edited.");
    }

}
