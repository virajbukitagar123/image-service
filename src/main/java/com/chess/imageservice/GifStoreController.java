package com.chess.imageservice;

import com.chess.imageservice.service.ObjectStoreService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@RestController
public class GifStoreController {

    private final ObjectStoreService objectStoreService;

    public GifStoreController(ObjectStoreService objectStoreService) {
        this.objectStoreService = objectStoreService;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/{openingName}/{move}")
    public ResponseEntity<Resource> returnGif(@PathVariable String openingName, @PathVariable int move, HttpServletResponse response) throws IOException {
        Optional<File> fileOp = objectStoreService.getGif(openingName, move);
        if(fileOp.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        response.setContentType("image/gif");
        Files.copy(fileOp.get().toPath(), response.getOutputStream());
        return ResponseEntity.ok().build();
    }
}
