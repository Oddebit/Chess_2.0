package pieces;

import env.PieceColor;
import env.Square;
import utils.MoveUtil;

import static pieces.Piece.PieceType.KNIGHT;

public class Knight extends Piece {

    public Knight(PieceColor pieceColor) {
        super(KNIGHT, pieceColor);
    }

    @Override
    public boolean isNaturalMove(Square destination) {
        Square position = getSquare();
        int dx = MoveUtil.getAbsDx(position, destination);
        int dy = MoveUtil.getAbsDy(position, destination);
        return ((dx == 1 && dy == 2)||(dx == 2 && dy == 1));
    }
}
