package pieces;

import env.PieceColor;
import env.Square;
import utils.MoveUtil;

import static pieces.Piece.PieceType.BISHOP;

public class Bishop extends Piece {

    public Bishop(PieceColor pieceColor) {
        super(BISHOP, pieceColor);
    }

    public boolean isNaturalMove(Square destination) {
        return MoveUtil.isDiagonal(getSquare(), destination);
    }
}
