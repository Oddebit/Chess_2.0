package pieces;

import env.PieceColor;
import env.Square;
import utils.MoveUtil;

import static pieces.Piece.PieceType.QUEEN;

public class Queen extends Piece{

    public Queen(PieceColor pieceColor) {
        super(QUEEN, pieceColor);
    }

    public boolean isNaturalMove(Square destination) {
        Square position = getSquare();
        return (MoveUtil.isVertical(position, destination)
                || MoveUtil.isHorizontal(position, destination)
                || MoveUtil.isDiagonal(position, destination));
    }
}
