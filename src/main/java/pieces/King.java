package pieces;

import env.Board;
import env.PieceColor;
import env.Square;
import utils.MoveUtil;

import java.awt.*;
import java.util.function.Predicate;

import static pieces.Piece.PieceType.KING;

public class King extends Piece {

    public King(PieceColor pieceColor) {
        super(KING, pieceColor);
    }

    @Override
    public boolean isNaturalMove(Square destination) {
        Square position = getSquare();
        return MoveUtil.isInRange(position, destination, 1)
                && (MoveUtil.isVertical(position, destination)
                || MoveUtil.isHorizontal(position, destination)
                || MoveUtil.isDiagonal(position, destination));
    }

    public boolean isCastling(Square destination, Board board) {
        Square position = getSquare();
        int orientation = MoveUtil.getDx(position, destination);
        Piece rook = board.getSquare(new Point(orientation > 0 ? 7 : 0, getPieceColor().getFirstRank())).getPiece();
        return !isHasMoved() &&
                rook != null && !rook.isHasMoved()
                && MoveUtil.isHorizontal(position, destination)
                && MoveUtil.isInRange(position, destination, 2)
                && !MoveUtil.isInRange(position, destination, 1)
                && !board.isCheck(this, board.getSquare(new Point(orientation > 0 ? 5 : 3, getPieceColor().getFirstRank())));

    }
}
