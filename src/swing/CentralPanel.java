package swing;

import lombok.Getter;
import model.Model;
import model.Plate;
import model.Tile;
import model.TileColor;
import swing.controler.SwingController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static model.Model.PLATES_COUNT;

@Getter
public class CentralPanel extends JPanel {
    private final Model model;
    private final SwingController controller;

    public CentralPanel(Model model, SwingController controller) {
        this.controller = controller;
        setLayout(new GridBagLayout());
        this.model = model;
        addTilesToPlates();
        addTilesToCenter();
    }

    private void addTilesToCenter() {
        int x = 0;
        for (TileColor value : TileColor.values()) {
            TileButtonWithCount tileButton = new TileButtonWithCount(value);
            add(
                    tileButton,
                    UiConstants.defineDefaultConstraint(x++, PLATES_COUNT + 1)
            );
            controller.getTileButtonWithCounts().add(tileButton);
        }

        refreshCentralButtons();
    }

    public void refreshCentralButtons() {
        Map<TileColor, List<Tile>> colorToTiles = model.getCenter()
                .stream()
                .collect(Collectors.groupingBy(Tile::getTileColor));

        for (TileButtonWithCount button : controller.getTileButtonWithCounts()) {
            button.setTiles(colorToTiles.get(button.getTileColor()));
        }
    }

    private void addTilesToPlates() {
        int y = 0;
        for (Plate plate : model.getPlates()) {
            List<TileButton> buttonList = new ArrayList<>(4);
            for (int x = 0; x < plate.getTiles().size(); x++) {
                Tile tile = plate.getTiles().get(x);
                TileButton tileButton = new TileButton(tile);
                buttonList.add(tileButton);
                add(
                        tileButton,
                        UiConstants.defineDefaultConstraint(x, y)
                );
                tileButton.addActionListener(e -> {
                    TileButton source = (TileButton) e.getSource();
                    Tile selectedTile = source.getTile();
                    Plate selectedPlate = model.findPlateByTile(selectedTile);
                    controller.setSelectedPlateTiles(model.findPlateTilesOfSameColor(selectedPlate, selectedTile), selectedPlate);
                });
            }
            y++;
            controller.getPlateToButtonsMap().put(plate, buttonList);
        }
    }

    public void removeTileFromPlate(Tile tile, Plate plate) {
        List<TileButton> buttonsInPlate = this.controller.getPlateToButtonsMap().get(plate);
        for (TileButton tileButton : buttonsInPlate) {
            if (tileButton.getTile().equals(tile)) {
                tileButton.setVisible(false);
            }
        }
    }
}
