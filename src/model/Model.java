package model;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
public class Model {
    private List<Player> players;
    private List<Plate> plates;
    private List<Tile> discard;
    private Center center;
    private List<Tile> bag;
    private GameStage gameStage;

    private final Tile penaltyTile = new Tile(TileColor.PENALTY);
    private Player activePlayer;
    public static final int PLAYER_COUNT = 2;
    public static final int PLATES_COUNT = PLAYER_COUNT * 2 + 1;
    private UiDelegate uiDelegate;

    public Model() {
        gameStage = GameStage.CHOICE_TILES;
        players = List.of(new Player("Player_1"), new Player("Player_2"));
        activePlayer = players.get(0);
        bag = new ArrayList<>(100);
        for (int i = 0; i < 100; i++) {
            bag.add(new Tile(TileColor.values()[i % 5]));
        }
        Collections.shuffle(bag);
        discard = new ArrayList<>(100);
        center = new Center();
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
        throw new RuntimeException("There is no plates containing tile " + tile);
    }

    public List<Tile> findPlateTilesOfSameColor(Plate plate, Tile selectedTile) {
        TileColor color = selectedTile.getTileColor();
        return plate.getTiles()
                .stream()
                .filter(color::sameAsTile)
                .collect(Collectors.toList());
    }

    public void moveTilesToStock(List<Tile> selectedPlateTiles, Place selectedPlace, StockLine selectedLine) {
        if (selectedPlace instanceof Center) {
            Center place = (Center) selectedPlace;
            Optional<Tile> penaltyTile = center.getCenterTiles().get(TileColor.PENALTY).stream().findFirst();
            penaltyTile.ifPresent(tile -> {
                center.remove(tile);
                moveTileToPenaltyLineInner(selectedPlace, tile);
            });
        }
        for (Tile selectedPlateTile : selectedPlateTiles) {
            selectedPlace.remove(selectedPlateTile);
            if (selectedLine.canAdd(selectedPlateTile)) {
                selectedLine.add(selectedPlateTile);
                this.uiDelegate.moveTileFromPlateToStock(selectedPlateTile, selectedPlace, selectedLine);
            } else {
                moveTileToPenaltyLineInner(selectedPlace, selectedPlateTile);
            }
        }
        throwRemainingTilesFromPlate(selectedPlace);
        switchPlayer();
        allTilesGrabbedCheck();
    }

    private void allTilesGrabbedCheck() {
        if (this.center.isEmpty() && allPlatesIsEmpty()){
            gameStage = GameStage.DISPOSE_TILES;
            this.uiDelegate.changeGameStage();
        }
    }

    private boolean allPlatesIsEmpty() {
        return plates.stream().allMatch(Plate::isEmpty);
    }

    private void throwRemainingTilesFromPlate(Place selectedPlace) {
        if (selectedPlace instanceof Center) {
            return;
        }
        List<Tile> selectedTiles = selectedPlace.getTiles();
        for (Tile tile : selectedTiles) {
            center.add(tile);
        }
        this.uiDelegate.moveTilesFromPlateToCenter(selectedPlace.getTiles(), selectedPlace, center.getTiles());
        selectedPlace.getTiles().clear();
    }

    private void switchPlayer() {
        int indexOfActivePlayer = players.indexOf(activePlayer);
        int nextPlayerIndex = (indexOfActivePlayer + 1) % players.size();
        activePlayer = players.get(nextPlayerIndex);
        this.uiDelegate.showActivePlayer(activePlayer);
    }

    private void moveTileToPenaltyLineInner(Place selectedPlate, Tile selectedPlateTile) {
        PenaltyLine penaltyLine = activePlayer.getPenaltyLine();
        if (penaltyLine.hasSpace()) {
            penaltyLine.add(selectedPlateTile);
            this.uiDelegate.moveTileFromPlateToPenalty(selectedPlateTile, selectedPlate, penaltyLine);
        } else {
            discard.add(selectedPlateTile);
            this.uiDelegate.moveTileFromPlateToDiscard(selectedPlateTile, selectedPlate, discard);
        }
    }

    public void moveTileToPenaltyLine(Place plate, List<Tile> selectedPlateTiles) {
        for (Tile selectedPlateTile : selectedPlateTiles) {
            plate.remove(selectedPlateTile);
            moveTileToPenaltyLineInner(plate, selectedPlateTile);
        }
        throwRemainingTilesFromPlate(plate);
        switchPlayer();
        allTilesGrabbedCheck();
    }
}
