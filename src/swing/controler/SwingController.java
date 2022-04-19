package swing.controler;

import lombok.Data;
import model.*;
import swing.GameView;
import swing.PlayerBoard;
import swing.TileButton;
import swing.TileButtonWithCount;

import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.geom.Point2D;
import java.security.KeyPair;
import java.util.*;
import java.util.List;

@Data
public class SwingController {

    public static final EmptyBorder EMPTY_BORDER = new EmptyBorder(1, 1, 1, 1);
    public static final LineBorder SELECTED_BUTTON_BORDER = new LineBorder(Color.MAGENTA, 4);
    public static final LineBorder SELECTED_STOCK_LINE_BORDER = new LineBorder(Color.GREEN, 4);
    private final Model model;
    private final GameView view;

    private final Map<TileButton, StockLine> stockButtonToStockLine = new HashMap<>();
    private final Map<Player, PlayerBoard> playerToBoard = new HashMap<>();
    private final Map<StockLine, List<TileButton>> stockLineToTileButtons = new HashMap<>();
    private final Map<Place, List<TileButton>> placeToButtonsMap = new HashMap<>();
    private final Map<PenaltyLine, List<TileButton>> penaltyLineToButtonsMap = new HashMap<>();
    private final List<TileButtonWithCount> tileButtonWithCounts = new ArrayList<>();
    private Place selectedPlace;
    private List<Tile> selectedPlaceTiles = Collections.emptyList();

    public SwingController(Model model, GameView view) {
        this.model = model;
        this.view = view;
    }

    public void bindStockButtonToLines(TileButton tileButton, StockLine stockLine) {
        stockButtonToStockLine.put(tileButton, stockLine);
    }

    public void clearSelection() {
        setSelectedPlaceTiles(Collections.emptyList(), null);
    }

    public void setSelectedPlaceTiles(List<Tile> placeTilesToSelect, Place placeToSelect) {
        Map<Place, List<TileButton>> plateToButtonsMap = getPlaceToButtonsMap();
        List<TileButton> previouslySelectedButtons =
                plateToButtonsMap.getOrDefault(this.selectedPlace, Collections.emptyList());
        for (TileButton tileButton : previouslySelectedButtons) {
            if (selectedPlaceTiles.contains(tileButton.getTile())){
                tileButton.setBorder(EMPTY_BORDER);
            }
        }
        for (TileButton tileButton : plateToButtonsMap.getOrDefault(placeToSelect, Collections.emptyList())) {
            if (placeTilesToSelect.contains(tileButton.getTile())){
                tileButton.setBorder(SELECTED_BUTTON_BORDER);
            }
        }

        selectedPlaceTiles = placeTilesToSelect;
        selectedPlace = placeToSelect;
    }

    public void bindPlayerToBoard(Player player, PlayerBoard playerBoard) {
        playerToBoard.put(player, playerBoard);
    }
}
