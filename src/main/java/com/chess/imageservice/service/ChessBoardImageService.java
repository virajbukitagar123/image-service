package com.chess.imageservice.service;

import com.chess.imageservice.board.Square;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.chess.imageservice.board.BoardBuilder.*;

// TODO: Can we create image before creating the Board List<List<Square>> directly from FEN?
@Service
public class ChessBoardImageService {

    public static final String IMAGES_PATH = System.getProperty("user.dir") + "/src/main/resources/static/";

    @Value("${chess.square_size}")
    private int SQUARE_SIZE;

    @Value("${chess.board_size}")
    private int BOARD_SIZE;
    
    private final ObjectStoreService objectStoreService;

    public ChessBoardImageService(ObjectStoreService objectStoreService) {
        this.objectStoreService = objectStoreService;
    }

    private List<List<Square>> convertToBoard(String fen) {
        List<List<Square>> board = new ArrayList<>();
        String[] fenSplit = fen.split(" ");
        String boardNotation = fenSplit[0];

        String[] ranks = boardNotation.split("/");

        for(int i = 0; i < 8; i++) {
            List<Square> rankToAdd = new ArrayList<>();
            for(char c: ranks[i].toCharArray()) {
                if(Character.isDigit(c)) {
                    int numEmptySquares = Integer.parseInt(String.valueOf(c));
                    for (int k = 0; k < numEmptySquares; k++) {
                        rankToAdd.add(emptySquare());
                    }
                } else {
                    switch(c) {
                        case 'p':
                            rankToAdd.add(blackPawn());
                            break;
                        case 'q':
                            rankToAdd.add(blackQueen());
                            break;
                        case 'n':
                            rankToAdd.add(blackKnight());
                            break;
                        case 'k':
                            rankToAdd.add(blackKing());
                            break;
                        case 'b':
                            rankToAdd.add(blackBishop());
                            break;
                        case 'r':
                            rankToAdd.add(blackRook());
                            break;
                        case 'P':
                            rankToAdd.add(whitePawn());
                            break;
                        case 'Q':
                            rankToAdd.add(whiteQueen());
                            break;
                        case 'K':
                            rankToAdd.add(whiteKing());
                            break;
                        case 'B':
                            rankToAdd.add(whiteBishop());
                            break;
                        case 'R':
                            rankToAdd.add(whiteRook());
                            break;
                        case 'N':
                            rankToAdd.add(whiteKnight());
                            break;
                    }
                }
            }
            board.add(rankToAdd);
        }
        return board;
    }

    public void printBoard(List<List<Square>> board) {
        System.out.println();
        for(List<Square> row: board) {
            for(Square square: row) {
                if(!square.isEmpty()) {
                    System.out.print(square.getPiece() + " ");
                } else {
                    System.out.print("-- ");
                }
            }
            System.out.println();
        }
    }

    public void toImage(String fen) {
        if(!objectStoreService.fenImageCreated(fen)) {
            List<List<Square>> board = convertToBoard(fen);
            try {
                BufferedImage boardImage = ImageIO.read(new File(IMAGES_PATH + "board.png"));
                boardImage = resizeImage(boardImage, BOARD_SIZE);
                BufferedImage combinedImage = new BufferedImage(boardImage.getWidth(), boardImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = combinedImage.createGraphics();
                g2d.drawImage(boardImage, 0, 0, null);

                List<List<Square>> b = board;

                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        Square sq = b.get(i).get(j);
                        if (!sq.isEmpty()) {
                            BufferedImage pieceFile = ImageIO.read(new File(IMAGES_PATH + sq.getPiece().toString() + ".png"));
                            pieceFile = resizeImage(pieceFile, SQUARE_SIZE);
                            addPiece(g2d, pieceFile, i, j);
                        }
                    }
                }

                g2d.dispose();
                objectStoreService.saveFenImage(combinedImage, fen);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void addPiece(Graphics2D combinedImage, BufferedImage pieceImage, int rank, int file) {
        combinedImage.drawImage(pieceImage, SQUARE_SIZE * file, SQUARE_SIZE * rank, null);
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int targetSize) {
        BufferedImage resizedImage = new BufferedImage(targetSize, targetSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(originalImage, 0, 0, targetSize, targetSize, null);
        g2d.dispose();
        return resizedImage;
    }
}
