package pieces;

import env.Board;
import env.PieceColor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PieceTest {

    @Test
    void testGetPiece() {
        assertEquals(Piece.PieceType.KING.getPiece(PieceColor.WHITE).toString(), "WHITE KING");
    }

    @Test
    void testAvailableMoves() {
        Board board = new Board(Board.StartingBoard.CLASSIC);
        Assertions.assertEquals(board.getAvailableMoves(board.getSquare("D2").getPiece()).size(), 2);
    }

//    ROOK
    @Test
    void testIsWayClearL_R() {
        testIsWayClear(new Rook(PieceColor.WHITE),
                "H4", "A4",
                "H3", "A3");
    }

    @Test
    void testIsWayClearR_L() {
        testIsWayClear(new Rook(PieceColor.WHITE),
                "A4", "H4",
                "A3", "H3");
    }

    @Test
    void testIsWayClearU_D() {
        testIsWayClear(new Rook(PieceColor.WHITE),
                "D8", "D1",
                "E8", "E1");
    }

    @Test
    void testIsWayClearD_U() {
        testIsWayClear(new Rook(PieceColor.WHITE),
                "D1", "D8",
                "E1", "E8");
    }


//    BISHOP
    @Test
    void testIsWayClearLU_RD() {
        testIsWayClear(new Bishop(PieceColor.WHITE),
                "A7", "G1",
                "A8", "H1");
    }

    @Test
    void testIsWayClearRU_LD() {
        testIsWayClear(new Bishop(PieceColor.WHITE),
                "H8", "A1",
                "H7", "B1");
    }

    @Test
    void testIsWayClearLD_RU() {
        testIsWayClear(new Bishop(PieceColor.WHITE),
                "A1", "H8",
                "B1", "H7");
    }

    @Test
    void testIsWayClearRD_LU() {
        testIsWayClear(new Bishop(PieceColor.WHITE),
                "G1", "A7",
                "H1", "A8");
    }

    @Test
    void testIsWayClearKnight() {

        Board board = new Board(Board.StartingBoard.EMPTY);

        Piece toMove = new Knight(PieceColor.WHITE);
        board.movePiece(toMove, board.getSquare("C4"));
        board.movePiece(new Pawn(PieceColor.BLACK), board.getSquare("D4"));

        Assertions.assertTrue(board.isWayClear(toMove, board.getSquare("B3")));
        Assertions.assertTrue(board.isWayClear(toMove, board.getSquare("B5")));
        Assertions.assertTrue(board.isWayClear(toMove, board.getSquare("C2")));
        Assertions.assertTrue(board.isWayClear(toMove, board.getSquare("C6")));
        Assertions.assertTrue(board.isWayClear(toMove, board.getSquare("E2")));
        Assertions.assertTrue(board.isWayClear(toMove, board.getSquare("E6")));
        Assertions.assertTrue(board.isWayClear(toMove, board.getSquare("F3")));
        Assertions.assertTrue(board.isWayClear(toMove, board.getSquare("F5")));
    }

    void testIsWayClear(Piece toMove,
                        String falsePosition, String falseDestination,
                        String truePosition, String trueDestination) {

        Board board = new Board(Board.StartingBoard.EMPTY);

        board.movePiece(toMove, board.getSquare(falsePosition));
        board.movePiece(new Pawn(PieceColor.BLACK), board.getSquare("D4"));

        Assertions.assertFalse(board.isWayClear(toMove, board.getSquare(falseDestination)));

        board.movePiece(toMove, board.getSquare(truePosition));

        Assertions.assertTrue(board.isWayClear(toMove, board.getSquare(trueDestination)));
    }
}