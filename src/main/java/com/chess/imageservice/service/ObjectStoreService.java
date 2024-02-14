package com.chess.imageservice.service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class ObjectStoreService {

    private static final String OBJECT_STORE_PATH = Paths.get(System.getProperty("user.dir"), "object-store").toString();
    private static final String FEN_STORE_PATH = Paths.get(OBJECT_STORE_PATH, "fen").toString();
    private static final String GIF_STORE_PATH = Paths.get(OBJECT_STORE_PATH, "gif").toString();

    private final Set<String> fenCache = new HashSet<>();

    @PostConstruct
    public void initCache() {
        try {
            // Get the directory path as a Path object
            Path dir = Paths.get(FEN_STORE_PATH);

            // Create a DirectoryStream to iterate over the files in the directory
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
                for (Path file : stream) {
                    // Process each file
                    fenCache.add(new String(Base64.getDecoder().decode(
                                            file.getFileName().toString().replace(".png", ""))));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean fenImageCreated(String fen) {
        return fenCache.contains(fen);
    }

    public void saveFenImage(BufferedImage combinedImage, String fen) throws IOException {
        String encodedFen = Base64.getEncoder().encodeToString(fen.getBytes());
        File output = new File(Paths.get(FEN_STORE_PATH,  encodedFen + ".png").toString());
        ImageIO.write(combinedImage, "png", output);
        fenCache.add(fen);
        log.info("Image saved for fen: " + fen);
    }

    public Path getFenImagePath(String fen) throws FileNotFoundException {
        String encodedFen = Base64.getEncoder().encodeToString(fen.getBytes());
        Path imagePath = Paths.get(FEN_STORE_PATH, encodedFen + ".png");

        if(Files.exists(imagePath)) {
            return imagePath;
        } else {
            throw new FileNotFoundException("File not found at " + imagePath);
        }
    }

    public void createGifsDirectory(String openingName) {
        Path directoryToCreate = Paths.get(GIF_STORE_PATH, openingName);

        try {
            // Create the directory
            Files.createDirectories(directoryToCreate);
            log.info("Directory created successfully for opening: " + openingName);
        } catch (IOException e) {
            log.error("Failed to create directory: " + e.getMessage());
        }
    }

    public Optional<File> getGif(String openingName, int move) {
        Path gifPath = Paths.get(GIF_STORE_PATH, openingName, move + ".gif");
        if(Files.exists(gifPath)) {
            return Optional.of(new File(gifPath.toUri()));
        }
        return Optional.empty();
    }

    public String getGifStorePath() {
        return GIF_STORE_PATH;
    }
}
