package swing;

import lombok.Getter;
import model.Tile;
import model.TileColor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Getter
public class TileButtonWithCount extends TileButton {

    private final TileColor tileColor;
    private final List<Tile> tiles = new ArrayList();

    public TileButtonWithCount(TileColor tileColor) {
        super(tileColor);
        this.tileColor = tileColor;
        this.setVisible(false);
        this.setBackground(defineColorByTileColor(tileColor));
        this.setForeground(Color.WHITE);
    }

    public void setTiles(List<Tile> tiles) {
        this.tiles.clear();
        this.setVisible(tiles != null);
        if (tiles == null) {
            return;
        }
        this.tiles.addAll(tiles);
        setText(String.valueOf(this.tiles.size()));
    }
}
