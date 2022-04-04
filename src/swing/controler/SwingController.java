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
import java.util.*;
import java.util.List;

@Data
public class SwingController {

    public static final EmptyBorder EMPTY_BORDER = new EmptyBorder(1, 1, 1, 1);
    public static final LineBorder SELECTED_BUTTON_BORDER = new LineBorder(Color.MAGENTA, 4);
    private final Model model;
    private final GameView view;

    private final Map<TileButton, StockLine> stockButtonToStockLine = new HashMap<>();
    private final Map<Player, PlayerBoard> playerToBoard = new HashMap<>();
    private final Map<StockLine, List<TileButton>> stockLineToTileButtons = new HashMap<>();
    private final Map<Plate, List<TileButton>> plateToButtonsMap = new HashMap<>();
    private final Map<PenaltyLine, List<TileButton>> penaltyLineToButtonsMap = new HashMap<>();
    private final List<TileButtonWithCount> tileButtonWithCounts = new ArrayList<>();

    private Plate selectedPlate;
    private List<Tile> selectedPlateTiles = Collections.emptyList();

    public SwingController(Model model, GameView view) {
        this.model = model;
        this.view = view;
    }

    public void bindStockButtonToLines(TileButton tileButton, StockLine stockLine) {
        stockButtonToStockLine.put(tileButton, stockLine);
    }

    public void clearSelection() {
        setSelectedPlateTiles(Collections.emptyList(), null);
    }

    public void setSelectedPlateTiles(List<Tile> plateTilesToSelect, Plate plateToSelect) {
        Map<Plate, List<TileButton>> plateToButtonsMap = getPlateToButtonsMap();
        List<TileButton> previouslySelectedButtons =
                plateToButtonsMap.getOrDefault(this.selectedPlate, Collections.emptyList());
        for (TileButton tileButton : previouslySelectedButtons) {
            if (selectedPlateTiles.contains(tileButton.getTile()))
                tileButton.setBorder(EMPTY_BORDER);
        }
        for (TileButton tileButton : plateToButtonsMap.getOrDefault(plateToSelect, Collections.emptyList())) {
            if (plateTilesToSelect.contains(tileButton.getTile()))
                tileButton.setBorder(SELECTED_BUTTON_BORDER);
        }

        selectedPlateTiles = plateTilesToSelect;
        selectedPlate = plateToSelect;
    }

    public void bindPlayerToBoard(Player player, PlayerBoard playerBoard) {
        playerToBoard.put(player, playerBoard);
    }
}
