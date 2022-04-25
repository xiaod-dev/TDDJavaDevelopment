import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;


public class Connect4TDDTest {
  private Connect4TDD tested;
  private OutputStream output;

  @BeforeEach
  public void setUp() throws Exception {
    output = new ByteArrayOutputStream();
    tested = new Connect4TDD(new PrintStream(output));
  }


  @Test
  public void whenGameStartedBoardShouldBeEmpty() {
    Assertions.assertEquals(tested.getNumberOfDiscs(), 0);
  }

  @Test()
  void whenDiscOutsideBoardThenRuntimeException() {
    int column = -1;
    Assertions.assertThrows(RuntimeException.class, () -> tested.putDiscInColumn(column),
        "Invalid column " + column);
  }

  @Test
  void whenFirstDiscInsertedInColumnThenPositionIsZero() {
    int column = 1;
    Assertions.assertEquals(tested.putDiscInColumn(column), 0);
  }

  @Test
  void whenSecondDiscInsertedInColumnThenPositionIsOne() {
    int column = 1;
    tested.putDiscInColumn(column);
    Assertions.assertEquals(tested.putDiscInColumn(column), 1);
  }

  @Test
  void whenDiscInsertedThenNumberOfDiscsIncreases() {
    int column = 1;
    tested.putDiscInColumn(column);
    Assertions.assertEquals(tested.getNumberOfDiscs(), 1);
  }

  @Test
  void whenNoMoreRoomInColumnThenRuntimeException() {
    int column = 1;
    int maxDiscsInColumn = 6; // thefor (int times = 0;
    for (int times = 0; times < maxDiscsInColumn; ++times) {
      tested.putDiscInColumn(column);
    }

    Assertions.assertThrows(RuntimeException.class, () -> tested.putDiscInColumn(column),
        "No more room in column " +
            column);

  }

  @Test
  void whenFirstPlayerPlaysThenDiscColorIsRed() {
    Assertions.assertEquals(tested.getCurrentPlayer(), "R");
  }

  @Test
  void whenSecondPlayerPlaysThenDiscColorIsGreen() {
    int column = 1;
    tested.putDiscInColumn(column);
    Assertions.assertEquals(tested.getCurrentPlayer(), "G");
  }

  @Test
  void whenAskedForCurrentPlayerThenOutputNotice() {
    tested.getCurrentPlayer();
    Assertions.assertTrue(output.toString().contains("Player R turn"));
  }

  @Test
  void whenADiscIsIntroducedTheBoardIsPrinted() {
    int column = 1;
    tested.putDiscInColumn(column);
    Assertions.assertTrue(output.toString().contains("| |R| | | | | |"));
  }

  @Test
  void whenTheGameStartsItIsNotFinished() {
    Assertions.assertFalse(tested.isFinished(), "The game must not be finished");
  }

  @Test
  void whenNoDiscCanBeIntroducedTheGamesIsFinished() {
    for (int row = 0; row < 6; row++)
      for (int column = 0; column < 7; column++)
        tested.putDiscInColumn(column);
    Assertions.assertTrue(tested.isFinished(), "The game must be finished");
  }

  @Test
  void when4VerticalDiscsAreConnectedThenPlayerWins() {
    for (int row = 0; row < 3; row++) {
      tested.putDiscInColumn(1); // R
      tested.putDiscInColumn(2); // G
    }
    Assertions.assertTrue(StringUtils.isBlank(tested.getWinner()));
    tested.putDiscInColumn(1); // R
    Assertions.assertEquals("R", tested.getWinner());
  }

  @Test
  void when4HorizontalDiscsAreConnectedThenPlayerWins() {
    int column;
    for (column = 0; column < 3; column++) {
      tested.putDiscInColumn(column); // R
      tested.putDiscInColumn(column); // G
    }
    Assertions.assertTrue(StringUtils.isBlank(tested.getWinner()));

    tested.putDiscInColumn(column); // R
    Assertions.assertEquals("R", tested.getWinner());
  }

  @Test
  void when4Diagonal1DiscsAreConnectedThenThatPlayerWins() {
    int[] gameplay =
        new int[]{1, 2, 2, 3, 4, 3, 3, 4, 4, 5, 4};
    for (int column : gameplay) {
      tested.putDiscInColumn(column);
    }
    Assertions.assertEquals("R", tested.getWinner());
  }

  @Test
  void when4Diagonal2DiscsAreConnectedThenThatPlayerWins() {
    int[] gameplay =
        new int[]{3, 4, 2, 3, 2, 2, 1, 1, 1, 1};

    for (int column : gameplay) {
      tested.putDiscInColumn(column);
    }
    Assertions.assertEquals("G", tested.getWinner());
  }
}