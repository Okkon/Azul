package model;

import lombok.Data;
import swing.TileButton;

import java.util.ArrayList;
import java.util.List;

@Data
public class Player {

    private String name;
    private int score;
    private List<StockLine> stockLines;
    private MainField mainField;
    private PenaltyLine penaltyLine;
    private List<List<TileButton>> mainFieldButtons;

    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.penaltyLine = new PenaltyLine();
        this.mainField = new MainField();
        this.stockLines = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            this.stockLines.add(new StockLine(i));
        }
        this.mainFieldButtons = new ArrayList<>();
    }



}
