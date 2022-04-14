package swing;

import lombok.Getter;
import model.Tile;
import model.TileColor;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Getter
public class TileButtonWithCount extends TileButton {

    private final TileColor tileColor;
    private final List<Tile> tiles = new ArrayList();
    private final JLabel countLabel = new JLabel();


    public TileButtonWithCount(TileColor tileColor) {
        super(tileColor);
        this.tileColor = tileColor;
        this.setVisible(false);
        this.setForeground(Color.WHITE);
//        setIconTextGap(0);
//        setBorder(null);
//        setHorizontalAlignment(SwingConstants.RIGHT);
//        this.setHorizontalTextPosition(SwingConstants.LEADING);

        countLabel.setAlignmentX(0.5f);
        countLabel.setOpaque(false);
        countLabel.setBackground(new Color(0x34000001, true));
        countLabel.setForeground(new Color(0xFF090809, true));
        countLabel.setFont(new Font("Arial", Font.BOLD, 20));
        this.add(countLabel);
    }

    public void setTiles(List<Tile> tiles) {
        this.tiles.clear();
        this.setVisible(tiles != null && tiles.size() != 0);
        if (tiles == null || tiles.size() == 0) {
            return;
        }
        defineImage(tiles.get(0));
        this.tiles.addAll(tiles);
        countLabel.setText(String.valueOf(this.tiles.size()));
    }

    @Override
    public Tile getTile() {
        return tiles.stream().findFirst().orElse(null);
    }
}
