package env;

import lombok.Getter;
import lombok.Setter;
import pieces.Piece;

import java.awt.*;

@Getter
@Setter
public class Square {

    private final Point position;
    private final PieceColor pieceColor;
    private Piece piece;

    public Square(int x, int y) {
        this.position = new Point(x, y);
        this.pieceColor = PieceColor.values()[(x + y) % 2];
    }

    public Point getPosition() {
        return new Point(position.x, position.y);
    }

    public boolean contains() {
        return piece != null;
    }

    public boolean contains(PieceColor pieceColor) {
        return contains() && piece.getPieceColor() == pieceColor;
    }

    public void render(Graphics graphics, int squareSize) {
        graphics.setColor(pieceColor.getGraphicColor());
        graphics.fillRect(position.x * squareSize, (7 - position.y) * squareSize, squareSize, squareSize);
        if(piece != null) piece.render(graphics, position.x * squareSize, (7 - position.y) * squareSize);
    }

    public void renderActive(Graphics graphics, int squareSize) {
        graphics.setColor(pieceColor.getGraphicColor());
        graphics.fillRect(position.x * squareSize, (7 - position.y) * squareSize, squareSize, squareSize);
        graphics.setColor(PieceColor.getOpponent(pieceColor).getGraphicColor());
        graphics.fillOval((int)(position.x * squareSize + 1/8d * squareSize), (int)((7 - position.y) * squareSize + 1/8d * squareSize),
                (int)(3/4d * squareSize), (int)(3/4d * squareSize));
        if(piece != null) piece.render(graphics, position.x * squareSize, (7 - position.y) * squareSize);
    }

    public void renderAvailable(Graphics graphics, int squareSize) {
        graphics.setColor(pieceColor.getGraphicColor());
        graphics.fillRect(position.x * squareSize, (7 - position.y) * squareSize, squareSize, squareSize);
        graphics.setColor(PieceColor.getOpponent(pieceColor).getGraphicColor());
        graphics.fillOval((int)(position.x * squareSize + 1/4d * squareSize), (int)((7 - position.y) * squareSize + 1/4d * squareSize),
                (int)(1/2d * squareSize), (int)(1/2d * squareSize));
        if(piece != null) piece.render(graphics, position.x * squareSize, (7 - position.y) * squareSize);
    }

    @Override
    public String toString() {
        return String.format("%c%d",(char) (position.x + 65), position.y + 1);
    }

    public static Point parseString(String string) {
        return new Point(string.charAt(0) - 65, Integer.parseInt(string.substring(1)) -1);
    }
}
