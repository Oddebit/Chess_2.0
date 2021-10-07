package env;

import lombok.Builder;
import lombok.Getter;
import lombok.With;
import pieces.*;
import utils.MoveUtil;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
public class Board {

    private final LinkedList<Square> squares = new LinkedList<>();

    public Board(StartingBoard startingBoard) {
        IntStream.rangeClosed(0, 7).forEach(x ->
                IntStream.rangeClosed(0, 7).forEach(y ->
                        squares.add(new Square(x, y))
                )
        );

        if (startingBoard == StartingBoard.EMPTY) return;
        initPawns();

        if (startingBoard == StartingBoard.CLASSIC)
            initPiecesClassic();
    }

    private void initPiecesClassic() {
        IntStream.of(0, 7).forEach(y -> {
                    y++;
                    PieceColor pieceColor = PieceColor.values()[getInitColor(y)];
                    movePiece(new Rook(pieceColor), getSquare("A" + y));
                    movePiece(new Knight(pieceColor), getSquare("B" + y));
                    movePiece(new Bishop(pieceColor), getSquare("C" + y));
                    movePiece(new Queen(pieceColor), getSquare("D" + y));
                    movePiece(new King(pieceColor), getSquare("E" + y));
                    movePiece(new Bishop(pieceColor), getSquare("F" + y));
                    movePiece(new Knight(pieceColor), getSquare("G" + y));
                    movePiece(new Rook(pieceColor), getSquare("H" + y));
                }
        );
    }

    public void initPawns() {
        IntStream.of(1, 6).forEach(y -> {
                    PieceColor pieceColor = PieceColor.values()[getInitColor(y)];
                    IntStream.rangeClosed(0, 7).forEach(x -> movePiece(new Pawn(pieceColor), getSquare(new Point(x, y))));
                }
        );
    }

    private int getInitColor(int row) {
        return row <= 3 ? 0 : 1;
    }

    private static Board copyOf(Board board) {
        return Board.parseArray(board.toArray());
    }

    public static Board copyOf(Board board, Piece piece, Square square) {
        return Board.parseArray(board.toArray(piece, square));
    }

    public List<Square> getAvailableMoves(Piece piece) {
        return squares.stream().filter(square -> canMove(piece, square))
                .collect(Collectors.toList());
    }

    public boolean tryToMovePiece(Piece piece, Square destination) {
        if(canMove(piece, destination)) {
            movePiece(piece, destination);
            return true;
        }
        return false;
    }

    public boolean canMove(Piece piece, Square destination) {
        return piece.getSquare() != destination
                && !destination.contains(piece.getPieceColor())
                && (piece.isNaturalMove(destination) || isSpecialMove(piece, destination))
                && isWayClear(piece, destination)
                && !isCheck(piece, destination);
    }

    private boolean isSpecialMove(Piece piece, Square destination) {
        switch (piece.getType()) {
            case KING:
                return ((King)piece).isCastling(destination, this);
            case PAWN:
                return ((Pawn)piece).isTaking(destination);
            default:
                return false;
        }
    }

    public void printMove(Piece piece, Square destination) {
        String move = String.format("%s to %s : ", piece.toString(), destination.toString());
        if (piece.getSquare() == destination) {
            move += "You're trying to put this piece on the square it's on.";
        }
        else if (destination.contains(piece.getPieceColor())) {
            move += "This square is occupied by a piece from the same color.";
        }
        else if (!piece.isNaturalMove(destination)) {
            move += "This is no natural move for this piece.";
        }
        else if (!isWayClear(piece, destination)) {
            move += "There is a piece in the way.";
        }
        else if (isCheck(piece, destination)) {
            move += "This move would put yourself in check.";
        } else {
            move += "OK.";
        }
        System.out.println(move);
    }

    public boolean isWayClear(Piece piece, Square destination) {
        if (piece.getType() == Piece.PieceType.KNIGHT) return true;

        Square position = piece.getSquare();
        int dx = MoveUtil.getDx(position, destination);
        int dy = MoveUtil.getDy(position, destination);

        Point point = new Point(position.getPosition());
        if (dx != 0) {
            int gradient = dy / dx;
            int orientation = dx / Math.abs(dx);
            for (int x = orientation, y = gradient * orientation;
                 (x - dx) * orientation < 0;
                 x += orientation, y += gradient * orientation) {
                point.move(point.x + orientation, point.y + gradient * orientation);
                if (getSquare(point).contains()) return false;
            }
        } else {
            int orientation = dy / Math.abs(dy);
            for (int y = orientation; (y - dy) * orientation < 0; y += orientation) {
                point.move(point.x, point.y + orientation);
                if (getSquare(point).contains()) return false;
            }
        }
        return true;
    }

    public void movePiece(Piece piece, Square destination) {
        if (piece == null) return;
        Square position = piece.getSquare();
        if (position != null) {
            position.setPiece(null);
            piece.setHasMoved(true);
        }
        piece.setSquare(destination);
        destination.setPiece(piece);
    }

    public boolean isCheck(Piece piece, Square square) {
        return Board.copyOf(this, piece, square).isCheck(piece.getPieceColor());
    }

    public boolean isCheck(PieceColor pieceColor) {
        Square kingPosition = squares.stream()
                .map(Square::getPiece)
                .filter(Objects::nonNull)
                .filter(piece -> piece.getType() == Piece.PieceType.KING)
                .filter(piece -> piece.getPieceColor() == pieceColor)
                .findFirst().orElseThrow().getSquare();

        return squares.stream()
                .map(Square::getPiece)
                .filter(Objects::nonNull)
                .filter(piece -> piece.getPieceColor() != pieceColor)
                .anyMatch(piece -> canMove(piece, kingPosition));
    }

    public Square getSquare(Point position) {
        return squares.stream()
                .filter(square -> square.getPosition().equals(position))
                .findAny().orElse(null);
    }

    public Square getSquare(String position) {
        return getSquare(Square.parseString(position));
    }

    public List<Piece> getPieces(PieceColor color) {
        return squares.stream()
                .filter(square -> square.contains(color))
                .map(Square::getPiece)
                .collect(Collectors.toList());
    }

    public void render(Graphics graphics, int squareSize, Piece activePiece, List<Square> availableMoves) {
        squares.forEach(square -> {
            Piece piece = square.getPiece();
            if(!availableMoves.isEmpty() && availableMoves.contains(square))
                square.renderAvailable(graphics, squareSize);
            else if(piece != null && piece.equals(activePiece))
                square.renderActive(graphics, squareSize);
            else
                square.render(graphics, squareSize);
        });
    }

    public String[][] toArray(Piece piece, Square square) {
        String[][] array = toArray();
        Point position = piece.getSquare().getPosition();
        array[position.x][position.y] = "";

        Point destination = square.getPosition();
        array[destination.x][destination.y] = piece.toString();
        return array;
    }

    public String[][] toArray() {
        String[][] array = new String[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = getSquare(new Point(i, j)).getPiece();
                array[i][j] = piece == null ? "" : piece.toString();
            }
        }
        return array;
    }

    public static Board parseArray(String[][] array) {
        Board board = new Board(StartingBoard.EMPTY);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board.movePiece(Piece.parseString(array[i][j]), board.getSquare(new Point(i, j)));
            }
        }
        return board;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Board)) return false;
        Board board = (Board) o;
        return Arrays.deepEquals(toArray(), board.toArray());
    }

    public enum StartingBoard {
        EMPTY,
        CLASSIC
    }
}
