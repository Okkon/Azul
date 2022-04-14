package model;

import lombok.Data;

import java.util.Collection;
import java.util.List;


public interface Place {
    List<Tile> tiles = null;

    void add(Tile tile);

    void remove(Tile tile);

    List<Tile> getTiles();
}
