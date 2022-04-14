package swing;

import lombok.Getter;
import model.*;
import swing.controler.SwingController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        List<TileButton> tileButtonWithCounts = new ArrayList<>();
        for (TileColor value : TileColor.values()) {
            TileButtonWithCount tileButton = new TileButtonWithCount(value);
            tileButtonWithCounts.add(tileButton);
            GridBagConstraints constraints = UiConstants.defineDefaultConstraint(x++, PLATES_COUNT + 1);
            add(
                    tileButton,
                    constraints
            );
            tileButton.addActionListener(e -> {
                TileButtonWithCount source = (TileButtonWithCount) e.getSource();
                controller.setSelectedPlaceTiles(source.getTiles(), model.getCenter());

            });
            controller.getTileButtonWithCounts().add(tileButton);
            controller.getPlaceToButtonsMap().put(model.getCenter(),tileButtonWithCounts);
        }

        refreshCentralButtons();
    }

    public void refreshCentralButtons() {
        for (TileButtonWithCount button : controller.getTileButtonWithCounts()) {
            button.setTiles(model.getCenter().getCenterTiles().get(button.getTileColor()));
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
                    controller.setSelectedPlaceTiles(model.findPlateTilesOfSameColor(selectedPlate, selectedTile), selectedPlate);
                });
            }
            y++;
            controller.getPlaceToButtonsMap().put(plate, buttonList);
        }
    }

    public void removeTileFromPlate(Tile tile, Place plate) {
        List<TileButton> buttonsInPlate = this.controller.getPlaceToButtonsMap().get(plate);
        for (TileButton tileButton : buttonsInPlate) {
            if (Objects.equals(tileButton.getTile(), tile)) {
                tileButton.setVisible(false);
            }
        }
    }
}
