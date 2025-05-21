package strategy;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import model.PlayerColor;

/**
 * This is the class that converts the mock model into text files.
 */
public class TranscriptToText {

  /**
   * Transcript to text class for testing.
   * @param filename filename.
   * @param transcript transcript to transcribe.
   */
  public static void writeTranscriptToFile(String filename, StringBuilder transcript) {
    try {
      Files.write(Paths.get(filename), transcript.toString().getBytes(StandardCharsets.UTF_8));
      System.out.println("Transcript written to " + filename);
    } catch (IOException e) {
      System.err.println("Error writing transcript to " + filename + ": " + e.getMessage());
      e.printStackTrace();
    }
  }

  /**
   * Main for playing game to create transcript.
   * @param args to use for main.
   */
  public static void main(String[] args) {
    StringBuilder fillFirstLog = new StringBuilder();
    MockPawnsBoard boardForFillFirst = new MockPawnsBoard(fillFirstLog, 5, 3);
    MockPlayer redPlayer = new MockPlayer(PlayerColor.RED);
    PawnsBoardStrategy fillFirst = new FillFirst();
    Move move1 = fillFirst.chooseMove(boardForFillFirst, redPlayer);

    writeTranscriptToFile("strategy-transcript-first.txt", fillFirstLog);

    StringBuilder maxRowLog = new StringBuilder();
    MockPawnsBoard boardForMaxRow = new MockPawnsBoard(maxRowLog, 5, 3);
    boardForMaxRow.getScores().set(0, new java.util.AbstractMap.SimpleEntry<>(0, 1));
    PawnsBoardStrategy maxRowStrategy = new MaximizeRowScore();
    Move move2 = maxRowStrategy.chooseMove(boardForMaxRow, redPlayer);

    writeTranscriptToFile("strategy-transcript-score.txt", maxRowLog);
  }
}
