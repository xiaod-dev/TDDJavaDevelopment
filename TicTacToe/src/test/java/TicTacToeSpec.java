import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class TicTacToeSpec {

    private TicTacToe ticTacToe;


    @BeforeEach
    void setUp() {
        ticTacToe = new TicTacToe();
    }

    @Test
    void whenXOutSideBoardThenThrowException() {
        assertThrows(RuntimeException.class, () -> ticTacToe.play(5, 2));
    }

    @Test
    void whenYOutsideBoardThenRuntimeException() {
        assertThrows(RuntimeException.class, () -> ticTacToe.play(2, 5));
    }

    @Test
    void whenOccupiedThenThrowsException() {
        assertThrows(RuntimeException.class, () -> {
            ticTacToe.play(1, 1);
            ticTacToe.play(1, 1);
        });
    }

    @Test
    void givenFirstTurnNextPlayerShouldBeX() {
        assertEquals(TicTacToe.Player.X, ticTacToe.nextPlayer());
    }

    @Test
    void givenLastTurnWasXWhenNextPlayerThenO() {
        ticTacToe.play(1, 1);
        assertEquals(TicTacToe.Player.O, ticTacToe.nextPlayer());
    }

    @Test
    void whenPlayThenNoWinner() {
        // default return No winner
        assertEquals(ticTacToe.play(1, 1), TicTacToe.Result.NO_WINNER);
    }

    @Test
    void whenPlayAndCompleteHorizontalLineThenWinner() {
        ticTacToe.play(1, 1); // X
        ticTacToe.play(1, 2); // O
        ticTacToe.play(2, 1); // X
        ticTacToe.play(2, 2); // O
        TicTacToe.Result result = ticTacToe.play(3, 1);// X
        assertEquals(TicTacToe.Result.X_WINNER, result);
    }

    @Test
    void whenPlayAndCompleteVerticalLineThenWinner() {
        ticTacToe.play(2, 1); // X
        ticTacToe.play(1, 1); // O
        ticTacToe.play(3, 1); // X
        ticTacToe.play(1, 2); // O
        ticTacToe.play(2, 2); // X
        final TicTacToe.Result result = ticTacToe.play(1, 3);// O
        assertEquals(TicTacToe.Result.O_WINNER, result);
    }

    @Test
    void whenPlayAndTopBottomDiagonalLineThenWinner() {
        /**
         * o   x
         *   x
         * x o
         */
        ticTacToe.play(1, 1); // X
        ticTacToe.play(1, 2); // O
        ticTacToe.play(2, 2); // X
        ticTacToe.play(1, 3); // O
        final TicTacToe.Result result = ticTacToe.play(3, 3);// x
        assertEquals(result, TicTacToe.Result.X_WINNER);
    }

    @Test
    void whenPlayAndBottomTopDiagonalLineThenWinner() {
        ticTacToe.play(1, 3); // X
        ticTacToe.play(1, 1); // O
        ticTacToe.play(2, 2); // X
        ticTacToe.play(1, 2); // O
        final TicTacToe.Result result = ticTacToe.play(3, 1);// x
        assertEquals(TicTacToe.Result.X_WINNER, result);
    }

    @Test
    void whenAllBoxesAreFilledThenDraw() {
        ticTacToe.play(1, 1);
        ticTacToe.play(1, 2);
        ticTacToe.play(1, 3);
        ticTacToe.play(2, 1);
        ticTacToe.play(2, 3);
        ticTacToe.play(2, 2);
        ticTacToe.play(3, 1);
        ticTacToe.play(3, 3);
        final TicTacToe.Result result = ticTacToe.play(3, 2);
        assertEquals(TicTacToe.Result.DRAW, result);
    }
}
