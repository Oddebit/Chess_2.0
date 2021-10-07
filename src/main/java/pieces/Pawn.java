package pieces;

import env.PieceColor;
import env.Square;
import utils.MoveUtil;

import static pieces.Piece.PieceType.PAWN;

public class Pawn extends Piece{

    public Pawn(PieceColor pieceColor) {
        super(PAWN, pieceColor);
    }

    @Override
    public boolean isNaturalMove(Square destination) {
        Square position = getSquare();
        return MoveUtil.isVertical(position, destination)
                && MoveUtil.isForward(position, destination, getPieceColor())
                && MoveUtil.isInRange(position, destination, isHasMoved()? 1: 2);
    }

    public boolean isTaking(Square destination) {
        Square position = getSquare();
        return MoveUtil.isDiagonal(position, destination)
                && MoveUtil.isForward(position, destination, getPieceColor())
                && MoveUtil.isInRange(position, destination, 1)
                && destination.contains(PieceColor.getOpponent(getPieceColor()));
    }
}
