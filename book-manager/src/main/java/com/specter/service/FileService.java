package com.specter.service;

import com.specter.entity.Books;
import com.specter.entity.FileBooks;
import com.specter.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;

    public FileBooks saveFile(MultipartFile file, Books filename) throws IOException {
        FileBooks fileBooks = new FileBooks().builder()
                .books(filename)
                .type(file.getContentType())
                .createAt(new Date())
                .data(file.getBytes()).build();
        return fileRepository.save(fileBooks);
    }

    public ResponseEntity<byte[]> downloadFile(Long fileId, String bookName) {
        Optional<FileBooks> fileEntity = fileRepository.findById(fileId);
        if (fileEntity.isPresent()) {
            FileBooks fileBooks = fileEntity.get();
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(fileBooks.getType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + bookName + "\"")
                    .body(fileBooks.getData());
        } else return ResponseEntity.notFound().build();
    }

    public Optional<FileBooks> getFile(Long id) {
        return fileRepository.findById(id);
    }

    public Optional<FileBooks> getFileByBookId(Long id) {
        return fileRepository.findByBooksId(id);
    }

    public void deleteBookFile(Long bookId) {
        fileRepository.deleteByBooksId(bookId);
    }
}
