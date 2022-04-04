package model;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class Model {
    private List<Player> players;
    private List<Plate> plates;
    private List<Tile> discard;
    private List<Tile> center;
    private List<Tile> bag;

    private final Tile penaltyTile = new Tile(TileColor.PENALTY);
    private Player activePlayer;
    public static final int PLAYER_COUNT = 2;
    public static final int PLATES_COUNT = PLAYER_COUNT * 2 + 1;
    private UiDelegate uiDelegate;

    public Model() {
        players = List.of(new Player("Player_1"), new Player("Player_2"));
        activePlayer = players.get(0);
        bag = new ArrayList<>(100);
        for (int i = 0; i < 100; i++) {
            bag.add(new Tile(TileColor.values()[i % 5]));
        }
        Collections.shuffle(bag);
        discard = new ArrayList<>(100);
        center = new ArrayList<>(16);
        plates = new ArrayList<>(PLATES_COUNT);
        for (int i = 0; i < PLATES_COUNT; i++) {
            plates.add(new Plate());
        }
        startTurn();
    }

    public void startTurn() {
        center.add(penaltyTile);
        for (int i = 0; i < PLATES_COUNT * 4; i++) {
            plates.get(i % PLATES_COUNT).add(takeFromBag(i));
        }
    }

    private Tile takeFromBag(int i) {
        if (bag.isEmpty()) {
            bag.addAll(discard);
            discard.clear();
        }
        return bag.remove(i);
    }

    public Plate findPlateByTile(Tile tile) {
        for (Plate plate : plates) {
            for (Tile plateTile : plate.getTiles()) {
                if (tile == plateTile) return plate;
            }
        }
        throw new RuntimeException();
    }

    public List<Tile> findPlateTilesOfSameColor(Plate plate, Tile selectedTile) {
        TileColor color = selectedTile.getTileColor();
        return plate.getTiles()
                .stream()
                .filter(color::sameAsTile)
                .collect(Collectors.toList());
    }

    public void moveTilesToStock(List<Tile> selectedPlateTiles, Plate selectedPlate, StockLine selectedLine) {
        for (Tile selectedPlateTile : selectedPlateTiles) {
            selectedPlate.remove(selectedPlateTile);
            if (selectedLine.canAdd(selectedPlateTile)) {
                selectedLine.add(selectedPlateTile);
                this.uiDelegate.moveTileFromPlateToStock(selectedPlateTile, selectedPlate, selectedLine);
            } else {
                moveTileToPenaltyLineInner(selectedPlate, selectedPlateTile);
            }
        }
        center.addAll(selectedPlate.getTiles());
        this.uiDelegate.moveTilesFromPlateToCenter(selectedPlate.getTiles(), selectedPlate, center);
        selectedPlate.getTiles().clear();
        switchPlayer();
    }

    private void switchPlayer() {
        int indexOfActivePlayer = players.indexOf(activePlayer);
        int nextPlayerIndex = (indexOfActivePlayer + 1) % players.size();
        activePlayer = players.get(nextPlayerIndex);
        this.uiDelegate.showActivePlayer(activePlayer);
    }

    private void moveTileToPenaltyLineInner(Plate selectedPlate, Tile selectedPlateTile) {
        PenaltyLine penaltyLine = activePlayer.getPenaltyLine();
        if (penaltyLine.hasSpace()) {
            penaltyLine.add(selectedPlateTile);
            this.uiDelegate.moveTileFromPlateToPenalty(selectedPlateTile, selectedPlate, penaltyLine);
        } else {
            discard.add(selectedPlateTile);
            this.uiDelegate.moveTileFromPlateToDiscard(selectedPlateTile, selectedPlate, discard);
        }
    }

    public void moveTileToPenaltyLine(Plate plate, List<Tile> selectedPlateTiles) {
        for (Tile selectedPlateTile : selectedPlateTiles) {
            plate.remove(selectedPlateTile);
            moveTileToPenaltyLineInner(plate, selectedPlateTile);
        }
        center.addAll(plate.getTiles());
        this.uiDelegate.moveTilesFromPlateToCenter(plate.getTiles(), plate, center);
        plate.getTiles().clear();
        switchPlayer();
    }
}
