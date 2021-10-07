package utils;

import env.PieceColor;
import env.Square;

public class MoveUtil {

    public static boolean isVertical(Square from, Square to) {
        return from.getPosition().getX() == to.getPosition().getX();
    }

    public static boolean isHorizontal(Square from, Square to) {
        return from.getPosition().getY() == to.getPosition().getY();
    }

    public static boolean isDiagonal(Square from, Square to) {
        return  getAbsDx(from, to) == getAbsDy(from, to);
    }

    public static boolean isInRange(Square from, Square to, int range) {
        return (getAbsDx(from, to) <= range && getAbsDy(from, to) <= range);
    }

    public static boolean isForward(Square from, Square to, PieceColor pieceColor) {
        return (to.getPosition().getY() - from.getPosition().getY())
                * (pieceColor == PieceColor.WHITE ? 1 : -1) > 0;
    }

    public static int getDx(Square from, Square to) {
        return (int) (to.getPosition().getX() - from.getPosition().getX());
    }

    public static int getDy(Square from, Square to) {
        return (int) (to.getPosition().getY() - from.getPosition().getY());
    }

    public static int getAbsDx(Square from, Square to) {
        return (int) Math.abs(getDx(from, to));
    }

    public static int getAbsDy(Square from, Square to) {
        return (int) Math.abs(getDy(from, to));
    }
}
