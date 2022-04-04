package model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PenaltyLine {
    private static final List<Integer> PENALTIES = List.of(1, 1, 2, 2, 2, 3, 3);
    public static final int MAX_SIZE = 7;
    private final List<Tile> tiles = new ArrayList<>(MAX_SIZE);

    public static Integer getPenalty(int i) {
        return PENALTIES.get(i);
    }

    public void add(Tile plateTile) {
        tiles.add(plateTile);
    }

    public boolean hasSpace() {
        return tiles.size() < MAX_SIZE;
    }
}
