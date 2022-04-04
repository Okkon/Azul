package model;

import java.util.List;

public interface UiDelegate {
    void moveTileFromPlateToStock(Tile selectedPlateTile, Plate plate, StockLine selectedLine);

    void moveTileFromPlateToPenalty(Tile selectedPlateTile, Plate plate, PenaltyLine penaltyLine);

    void moveTileFromPlateToDiscard(Tile selectedPlateTile, Plate plate, List<Tile> discard);

    void moveTilesFromPlateToCenter(List<Tile> tiles, Plate plate, List<Tile> center);

    void showActivePlayer(Player activePlayer);
}
