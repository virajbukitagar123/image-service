package com.chess.imageservice.service;

import com.chess.backend.dto.Position;
import com.madgag.gif.fmsware.AnimatedGifEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ImageGifConvertor {

    private final ObjectStoreService objectStoreService;


    public ImageGifConvertor(ObjectStoreService objectStoreService) {
        this.objectStoreService = objectStoreService;
    }

    public void convertGifsForAllPositions(String openingName, List<Position> positions) {
        objectStoreService.createGifsDirectory(openingName);
        List<Path> imagePaths = new ArrayList<>(positions.stream().map(pos -> {
            try {
                return objectStoreService.getFenImagePath(pos.getPosition());
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }).toList());
        int n = imagePaths.size();

        for(int i = 0; i < n - 1; i++) {
            int move = i + 1;
            convertGifForPosition(openingName, imagePaths, move);
            imagePaths.remove(0);
        }
    }

    private void convertGifForPosition(String openingName, List<Path> imagePaths, int move) {
        try {
            BufferedImage[] images = new BufferedImage[imagePaths.size()];

            // Load PNG images into BufferedImage objects
            for (int i = 0; i < imagePaths.size(); i++) {
                images[i] = ImageIO.read(new File(imagePaths.get(i).toUri()));
            }

            AnimatedGifEncoder e = new AnimatedGifEncoder();
            e.start(Paths.get(objectStoreService.getGifStorePath(), openingName, move + ".gif").toString());
            e.setDelay(1000);

            for (BufferedImage img : images) {
                e.addFrame(img);
            }

            e.finish();
            log.info("Gif Created for opening {} from move: {}", openingName, move);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
