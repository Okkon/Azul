package swing;

import lombok.Getter;
import model.*;
import swing.controler.SwingController;

import javax.swing.*;
import java.awt.*;
import java.util.List;

@Getter
public class GameView implements UiDelegate {

    private final Model model;
    private final JLabel activePlayerText;
    private final JPanel infoPanel;
    private final JLabel stageText;
    private PlayerBoard playerBoard1;
    private PlayerBoard playerBoard2;
    private CentralPanel centralPanel;
    private SwingController controller;

    public GameView(Model model) {
        this.model = model;
        stageText = new JLabel(model.getGameStage().toString());
        activePlayerText = new JLabel("ACTIVE PLAYER : " + model.getActivePlayer().getName());
        infoPanel = new JPanel();
        infoPanel.add(activePlayerText);
        infoPanel.add(stageText);
    }

    public void start() {
        JFrame f = new JFrame();

        f.setLayout(new BorderLayout(5, 20));
        f.setVisible(true);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);


        List<Player> players = model.getPlayers();
        playerBoard1 = new PlayerBoard(players.get(0), controller);
        playerBoard2 = new PlayerBoard(players.get(1), controller);
        centralPanel = new CentralPanel(model, controller);

        f.add(infoPanel, BorderLayout.NORTH);
        f.add(playerBoard1, BorderLayout.WEST);
        f.add(playerBoard2, BorderLayout.EAST);
        f.add(centralPanel);

        f.revalidate();
        f.repaint();

    }

    public void setController(SwingController controller) {
        this.controller = controller;
    }

    @Override
    public void moveTileFromPlateToStock(Tile selectedPlateTile, Place plate, StockLine selectedLine) {
        centralPanel.removeTileFromPlate(selectedPlateTile, plate);
        List<TileButton> stockLineButtons = controller.getStockLineToTileButtons().get(selectedLine);
        int indexOfSelectedTile = selectedLine.getTiles().indexOf(selectedPlateTile);
        stockLineButtons.get(indexOfSelectedTile).setTile(selectedPlateTile);
    }

    @Override
    public void moveTileFromPlateToPenalty(Tile selectedPlateTile, Place plate, PenaltyLine penaltyLine) {
        centralPanel.removeTileFromPlate(selectedPlateTile, plate);
        List<TileButton> penaltyLineButtons = controller.getPenaltyLineToButtonsMap().get(penaltyLine);
        int indexOfSelectedTile = penaltyLine.getTiles().indexOf(selectedPlateTile);
        penaltyLineButtons.get(indexOfSelectedTile).setTile(selectedPlateTile);
    }

    @Override
    public void moveTileFromPlateToDiscard(Tile selectedPlateTile, Place plate, List<Tile> discard) {
        centralPanel.removeTileFromPlate(selectedPlateTile, plate);
    }

    @Override
    public void moveTilesFromPlateToCenter(List<Tile> tiles, Place plate, List<Tile> center) {
        for (Tile tile : tiles) {
            centralPanel.removeTileFromPlate(tile, plate);
        }
        centralPanel.refreshCentralButtons();
    }

    @Override
    public void showActivePlayer(Player activePlayer) {
        activePlayerText.setText("ACTIVE PLAYER : " + activePlayer.getName());
    }

    @Override
    public void changeGameStage() {
        stageText.setText(model.getGameStage().toString());
    }
}
