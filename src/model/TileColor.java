package model;

public enum TileColor {
    BLACK,
    BLUE,
    RED,
    YELLOW,
    AZURE,
    PENALTY;

    public boolean sameAsTile(Tile tile) {
        return equals(tile.getTileColor());
    }
}
