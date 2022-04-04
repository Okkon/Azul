package model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class StockLine {
    private List<Tile> tiles;
    private final int size;

    public StockLine(int size) {
        this.tiles = new ArrayList<>(size);
        this.size = size;
    }

    public void add(Tile plateTile) {
        tiles.add(plateTile);
    }

    public Tile get(int i) {
        return i < tiles.size() ? tiles.get(i) : null;
    }

    public int getSize() {
        return size;
    }

    public boolean hasSpace() {
        return tiles.size() < size;
    }

    public boolean canAdd(Tile tile) {
        if (!hasSpace()) {
            return false;
        }
        if (tiles.isEmpty()) {
            return true;
        }
        return tiles.get(0).getTileColor().equals(tile.getTileColor());
    }
}
