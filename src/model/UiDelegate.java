package model;

import java.util.List;

public interface UiDelegate {
    void moveTileFromPlateToStock(Tile selectedPlateTile, Place plate, StockLine selectedLine);

    void moveTileFromPlateToPenalty(Tile selectedPlateTile, Place plate, PenaltyLine penaltyLine);

    void moveTileFromPlateToDiscard(Tile selectedPlateTile, Place plate, List<Tile> discard);

    void moveTilesFromPlateToCenter(List<Tile> tiles, Place plate, List<Tile> center);

    void showActivePlayer(Player activePlayer);

    void changeGameStage();
}
