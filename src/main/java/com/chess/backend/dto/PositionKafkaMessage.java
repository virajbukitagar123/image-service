// In this package to make sure that Kafka consumer finds the deserializing class in the same package from where it was sent. Lame :(

package com.chess.backend.dto;

public class PositionKafkaMessage {
    private String command;
    private String opening;
    private String fen;

    public PositionKafkaMessage(String command, String opening, String fen) {
        this.command = command;
        this.opening = opening;
        this.fen = fen;
    }

    public PositionKafkaMessage() {
    }

    public String getCommand() {
        return command;
    }

    public String getOpening() {
        return opening;
    }

    public String getFen() {
        return fen;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void setOpening(String opening) {
        this.opening = opening;
    }

    public void setFen(String fen) {
        this.fen = fen;
    }

    @Override
    public String toString() {
        return "PositionKafkaMessage{" +
                "command=" + command +
                ", opening='" + opening + '\'' +
                ", fen='" + fen + '\'' +
                '}';
    }
}
