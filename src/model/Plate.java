package model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Plate implements Place {
    private List<Tile> tiles = new ArrayList<>(4);

    public void add(Tile tile) {
        tiles.add(tile);
    }

    public void remove(Tile plateTile) {
        tiles.remove(plateTile);
    }

    public boolean isEmpty() {
        return getTiles().isEmpty();
    }
}
