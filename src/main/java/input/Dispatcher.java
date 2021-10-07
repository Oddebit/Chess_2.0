package input;

import env.Board;
import env.PieceColor;
import env.Square;
import lombok.Setter;
import pieces.Piece;

import java.awt.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Dispatcher {
    @Setter
    private Graphics graphics;

    private final Board board;
    private final int squareSize;

    private Piece activePiece;
    private PieceColor activePieceColor;

    private List<Square> availableMoves = new ArrayList<>();

    public Dispatcher() {
        this.board = new Board(Board.StartingBoard.CLASSIC);
        this.squareSize = 80;
        this.activePieceColor = PieceColor.WHITE;
    }

    public void click(Point point) {
        Square selectedSquare = getSquare(point);
        Piece selectedPiece = selectedSquare.getPiece();
        if (activePiece == null) {
            if (selectedPiece == null) return;
            PieceColor selectedPieceColor = selectedPiece.getPieceColor();
            if (selectedPieceColor == activePieceColor) selectPiece(selectedPiece);
        } else {
            if (selectedPiece == null) {
                moveActivePiece(selectedSquare);
                return;
            }
            PieceColor selectedPieceColor = selectedPiece.getPieceColor();
            if (selectedPieceColor == activePieceColor) selectPiece(selectedPiece);
            else moveActivePiece(selectedSquare);
        }
    }

    private void selectPiece(Piece piece) {
        activePiece = piece;
        long start = System.currentTimeMillis();
        availableMoves = board.getAvailableMoves(activePiece);
        System.out.println(System.currentTimeMillis() - start);
    }

    private void moveActivePiece(Square square) {
        availableMoves.clear();
        boolean moved = board.tryToMovePiece(activePiece, square);
        if (moved) {
            activePieceColor = PieceColor.getOpponent(activePieceColor);
            checkCheckMate(activePieceColor);
        }
        activePiece = null;
    }

    private void checkCheckMate(PieceColor pieceColor) {
        if(board.getPieces(pieceColor).stream()
                .allMatch(piece -> board.getAvailableMoves(piece).isEmpty()))
            System.out.println("CHECK MATE, MATE");
    }

    private Square getSquare(Point point) {
        return board.getSquare(new Point(point.x / squareSize, 7 - point.y / squareSize));
    }


    public Dimension getBoardDimension() {
        int boardSize = squareSize * 8;
        return new Dimension(boardSize + 16, boardSize + 39);
    }

    public void render() {
        board.render(graphics, squareSize, activePiece, availableMoves);
    }
}
