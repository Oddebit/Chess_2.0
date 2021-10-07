package pieces;

import env.PieceColor;
import env.Square;
import utils.MoveUtil;

import static pieces.Piece.PieceType.ROOK;

public class Rook extends Piece {

    public Rook(PieceColor pieceColor) {
        super(ROOK, pieceColor);
    }

    public boolean isNaturalMove(Square destination) {
        Square position = getSquare();
        return (MoveUtil.isVertical(position, destination)
                || MoveUtil.isHorizontal(position, destination));
    }
}
