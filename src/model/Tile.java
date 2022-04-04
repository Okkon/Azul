package model;

import lombok.Getter;

@Getter
public class Tile {
    private TileColor tileColor;

    public Tile(TileColor tileColor) {
        this.tileColor = tileColor;
    }
}
