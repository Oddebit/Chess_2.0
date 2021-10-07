package env;

import lombok.Getter;

import java.awt.*;

public enum PieceColor {
    WHITE(new Color(255, 185, 185)),
    BLACK(new Color(0, 70, 70));

    @Getter
    private final java.awt.Color graphicColor;

    PieceColor(java.awt.Color graphicColor) {
        this.graphicColor = graphicColor;
    }

    public static PieceColor getOpponent(PieceColor pieceColor) {
        PieceColor[] pieceColors = PieceColor.values();
        return pieceColors[(pieceColor.ordinal() + 1) % pieceColors.length];
    }

    public int getFirstRank() {
        return ordinal() * 7;
    }

    public int getForward() {
        return ordinal() == 0 ? 1 : -1;
    }
}
