package pieces;

import env.PieceColor;
import env.Square;
import lombok.Getter;
import lombok.Setter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

@Getter
@Setter
public abstract class Piece {

    private final PieceType type;
    private final PieceColor pieceColor;

    private Square square;
    private boolean hasMoved;

    private Image image;
    private Frame frame;

    private ArrayList<Point> moveIncrements = new ArrayList<>();
    private int range;

    public Piece(PieceType type, PieceColor pieceColor) {
        this.type = type;
        this.pieceColor = pieceColor;
        this.hasMoved = false;
        try {
            this.image = ImageIO.read(new File("res/sprite.png"))
                    .getSubimage(133 * type.ordinal(), 133 * pieceColor.ordinal(), 133, 133)
                    .getScaledInstance(80, 80, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.frame = new Frame();
    }

    public abstract boolean isNaturalMove(Square destination);

    @Override
    public String toString() {
        return String.format("%s %s", getPieceColor(), getType());
    }

    public static Piece parseString(String string) {
        if (string.equals("")) return null;
        String[] values = string.split(" ");
        PieceColor color = PieceColor.valueOf(values[0]);
        PieceType type = PieceType.valueOf(values[1]);
        return type.getPiece(color);
    }

    public void render(Graphics graphics, int x, int y) {
        graphics.drawImage(image, x, y, frame);
    }

    public enum PieceType {
        KING (King.class),
        QUEEN (Queen.class),
        BISHOP (Bishop.class),
        KNIGHT (Knight.class),
        ROOK (Rook.class),
        PAWN (Pawn.class);

        private final Class<? extends Piece> pieceClass;

        PieceType(Class<? extends Piece> pieceClass) {
            this.pieceClass = pieceClass;
        }

        public Piece getPiece(PieceColor color) {
            try {
                return pieceClass.getDeclaredConstructor(PieceColor.class).newInstance(color);
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
