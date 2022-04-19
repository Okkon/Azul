package model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class Center implements Place {
    private Map<TileColor, List<Tile>> centerTiles = new HashMap<>();

    public Center() {
        for (TileColor color : TileColor.values()) {
            centerTiles.put(color, new ArrayList<>());
        }
    }

    @Override
    public void add(Tile tile) {
        List<Tile> sameColorTiles = centerTiles.get(tile.getTileColor());
        if (sameColorTiles == null) {
            centerTiles.put(tile.getTileColor(), List.of(tile));
            return;
        }
        centerTiles.get(tile.getTileColor()).add(tile);
    }

    @Override
    public void remove(Tile tile) {
        centerTiles.get(tile.getTileColor()).remove(tile);
    }

    @Override
    public List<Tile> getTiles() {
        ArrayList<Tile> tiles = new ArrayList<>();
        for (List<Tile> list : centerTiles.values()) {
            tiles.addAll(list);
        }
        return tiles;
    }

    public boolean isEmpty() {
        for (Map.Entry<TileColor, List<Tile>> entry : centerTiles.entrySet()) {
            if (!entry.getValue().isEmpty()){
                return false;
            }
        }
        return true;
    }
}
