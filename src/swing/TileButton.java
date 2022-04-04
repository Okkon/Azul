package swing;

import lombok.Getter;
import model.Tile;
import model.TileColor;

import javax.swing.*;
import java.awt.*;

import static swing.UiConstants.*;

@Getter
public class TileButton extends JButton {
    private Tile tile;

    public TileButton(Tile tile) {
        super();
        this.tile = tile;

        setDefaultSize();

        this.setBackground(defineColor(tile));
        defineImage(tile);
    }

    private void defineImage(Tile tile) {
        if (tile == null) {
            return;
        }
        this.setIcon(switch (tile.getTileColor()) {
            case AZURE -> ICON_AZURE;
            case RED -> ICON_BLACK;
            case BLACK -> ICON_RED;
            case BLUE -> ICON_BLUE;
            case YELLOW -> ICON_YELLOW;
            case PENALTY -> ICON_BLACK;
        });
    }

    private void setDefaultSize() {
        this.setMinimumSize(SIZE);
        this.setPreferredSize(SIZE);
        this.setMaximumSize(SIZE);
        this.setMargin(NO_INSETS);
    }

    public TileButton(TileColor tileColor) {
        super();

        setDefaultSize();

        this.setBackground(defineColorByTileColor(tileColor));
    }

    protected Color defineColor(Tile tile) {
        if (tile == null) {
            return Color.gray;
        }
        return defineColorByTileColor(tile.getTileColor());
    }

    protected Color defineColorByTileColor(TileColor tileColor) {
        return switch (tileColor) {
            case AZURE -> Color.CYAN;
            case RED -> Color.red;
            case BLACK -> Color.BLACK;
            case BLUE -> Color.BLUE;
            case YELLOW -> Color.YELLOW;
            case PENALTY -> Color.GREEN;
        };
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
        this.setBackground(defineColor(tile));
        defineImage(tile);
    }
}
