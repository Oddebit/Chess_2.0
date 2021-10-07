package env;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pieces.Piece;

import java.awt.*;
import java.util.Arrays;

public class BoardTest {

    @Test
    void testInitClassicBoard() {
        Board from = new Board(Board.StartingBoard.CLASSIC);
        printBoard(from);
    }

    @Test
    void testInitEmptyBoard() {
        Board from = new Board(Board.StartingBoard.EMPTY);
        printBoard(from);
    }

    @Test
    void testPrintSquares() {
        Board board =  new Board(Board.StartingBoard.EMPTY);
        board.getSquares().forEach(System.out::println);
    }

    @Test
    void testGetSquare() {
        Board board =  new Board(Board.StartingBoard.CLASSIC);
        Piece piece = board.getSquare("A1").getPiece();
        Assertions.assertEquals(piece.getType(), Piece.PieceType.ROOK);
        Assertions.assertEquals(piece.getPieceColor(), PieceColor.WHITE);
    }

    @Test
    void testBoardToDifferentArray() {
        Board from = new Board(Board.StartingBoard.CLASSIC);
        Piece piece = from.getSquare(new Point(0, 0)).getPiece();
        Square square = from.getSquare(new Point(0, 3));
        Arrays.stream(from.toArray(piece, square)).forEach(strings -> {
            System.out.println();
            Arrays.stream(strings).forEach(string -> System.out.printf(" - %s", string));
        });
    }

    @Test
    void testBoardToSameArray() {
        Board from = new Board(Board.StartingBoard.CLASSIC);
        String[][] array = from.toArray();
        Arrays.stream(array).forEach(strings -> {
            System.out.println();
            Arrays.stream(strings).forEach(string -> System.out.printf(" - %s", string));
        });
        Board to = Board.parseArray(array);
        Assertions.assertEquals(from, to);
    }

    @Test
    void testIsCheck() {
        Board board = new Board(Board.StartingBoard.CLASSIC);

        Piece whiteQueen = board.getSquare("D1").getPiece();
        Square e3 = board.getSquare("E3");
        board.movePiece(whiteQueen, e3);

        printBoard(board);
        System.out.println("\n\n" + "*".repeat(121));

        Piece blackKingPawn = board.getSquare("E7").getPiece();
        Square d6 = board.getSquare("D6");

        printBoard(Board.copyOf(board, blackKingPawn, d6));

        Assertions.assertTrue(board.isCheck(blackKingPawn, d6));
    }

    public static void printBoard(Board board) {
        Arrays.stream(board.toArray()).forEach(strings -> {
            System.out.println();
            Arrays.stream(strings).forEach(string -> System.out.printf("| %-12s ", string));
            System.out.print("|");
        });
    }
}