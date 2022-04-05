package swing;

import lombok.Getter;
import model.*;
import swing.controler.SwingController;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static java.awt.GridBagConstraints.WEST;
import static model.GameConstants.*;

@Getter
public class PlayerBoard extends JPanel {

    private final Player player;
    private final SwingController controller;

    public PlayerBoard(Player player, SwingController controller) {
        this.player = player;
        this.controller = controller;
        setLayout(new GridBagLayout());
        controller.bindPlayerToBoard(player, this);

        createScoreDisplay();
        createNameLabel();
        createStockLineElements();
        createMainFieldElements();
        createPenaltyLineElements();
    }

    private void createNameLabel() {
        JLabel label = new JLabel(player.getName());
        GridBagConstraints constraints = UiConstants.defineDefaultConstraint(0, 1);
        constraints.gridwidth = 3;
        constraints.anchor = WEST;
        add(
                label,
                constraints
        );
    }

    private void createScoreDisplay() {
        JLabel label = new JLabel("Score: " + player.getScore());
        GridBagConstraints constraints = UiConstants.defineDefaultConstraint(0, 2);
        constraints.gridwidth = 2;
        constraints.anchor = WEST;
        add(
                label,
                constraints
        );
    }

    private void createPenaltyLineElements() {
        List<TileButton> tileButtons = new ArrayList<>();
        for (int i = 0; i < PenaltyLine.MAX_SIZE; i++) {
            TileButton tileButton = new TileButton((Tile) null);
            tileButton.setText(String.format("-%s", PenaltyLine.getPenalty(i)));
            add(
                    tileButton,
                    UiConstants.defineDefaultConstraint(i, 5)
            );
            tileButtons.add(tileButton);
            tileButton.addActionListener(e -> {
                Plate selectedPlate = controller.getSelectedPlate();
                List<Tile> selectedPlateTiles = controller.getSelectedPlateTiles();
                controller.getModel().moveTileToPenaltyLine(selectedPlate, selectedPlateTiles);
                controller.clearSelection();
            });
        }
        controller.getPenaltyLineToButtonsMap().put(player.getPenaltyLine(), tileButtons);
    }

    private void createMainFieldElements() {
        for (int x = 0; x < MAX_STOCK_LINE_SIZE; x++) {
            for (int y = 0; y < 5; y++) {
                TileButton tileButton = new TileButton((Tile) null);
                tileButton.setText(String.format("%s:%s", x, y));
                add(
                        tileButton,
                        UiConstants.defineDefaultConstraint(x + MAX_STOCK_LINE_SIZE, y)
                );
            }
        }
    }

    private void createStockLineElements() {
        int y = -1;
        for (StockLine stockLine : player.getStockLines()) {
            y++;
            int size = stockLine.getSize();
            List<TileButton> tileButtons = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                int x = 4 - y + i;
                Tile tile = stockLine.get(i);
                TileButton tileButton = new TileButton(tile);
                tileButtons.add(tileButton);
                add(
                        tileButton,
                        UiConstants.defineDefaultConstraint(x, y)
                );
                controller.bindStockButtonToLines(tileButton, stockLine);
                tileButton.addActionListener(e -> {
                    if (!player.equals(controller.getModel().getActivePlayer())){
                        return;
                    }
                    TileButton source = (TileButton) e.getSource();
                    StockLine selectedLine = controller.getStockButtonToStockLine().get(source);
                    Plate selectedPlate = controller.getSelectedPlate();
                    List<Tile> selectedPlateTiles = controller.getSelectedPlateTiles();
                    Model model = controller.getModel();
                    model.moveTilesToStock(selectedPlateTiles, selectedPlate, selectedLine);
                    controller.clearSelection();
                });
            }
            controller.getStockLineToTileButtons().put(stockLine, tileButtons);
        }
    }
}
