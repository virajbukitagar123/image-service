package com.chess.imageservice.service;

import com.chess.backend.dto.Position;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class KafkaConsumerService {
    private final OpeningsRepository openingsRepository;
    private final ChessBoardImageService chessBoardImageService;
    private final ImageGifConvertor imageGifConvertor;

    public KafkaConsumerService(
            OpeningsRepository openingsRepository,
            ChessBoardImageService chessBoardImageService,
            ImageGifConvertor imageGifConvertor) {
        this.openingsRepository = openingsRepository;
        this.chessBoardImageService = chessBoardImageService;
        this.imageGifConvertor = imageGifConvertor;
    }

    @KafkaListener(id = "chess_id", topics = "chess_moves", containerFactory = "kafkaListenerContainerFactory")
    public void listen(String openingName) {
        openingName = openingName.replace("\"", "");
        log.info("Opening Received: " + openingName);
        List<Position> openingPositions = openingsRepository.findPositionsByOpening(openingName);
        for(Position pos: openingPositions) {
            chessBoardImageService.toImage(pos.getPosition());
        }
        imageGifConvertor.convertGifsForAllPositions(openingName, openingPositions);
    }
}
