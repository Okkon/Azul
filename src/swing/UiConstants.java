package swing;

import lombok.experimental.UtilityClass;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

@UtilityClass
public class UiConstants implements Serializable {

    public static final int SIZE_INT = 45;
    public static final Dimension SIZE = new Dimension(SIZE_INT, SIZE_INT);
    public static final Insets INSETS = new Insets(5, 5, 5, 5);
    public static final Insets NO_INSETS = new Insets(0, 0, 0, 0);
    public static final ImageIcon ICON_BLACK = getResizedImageIcon("AZUL_BLACK");
    public static final ImageIcon ICON_AZURE = getResizedImageIcon("AZUL_AZURE");
    public static final ImageIcon ICON_YELLOW = getResizedImageIcon("AZUL_YELLOW");
    public static final ImageIcon ICON_BLUE = getResizedImageIcon("AZUL_BLUE");
    public static final ImageIcon ICON_RED = getResizedImageIcon("AZUL_RED");

    private static ImageIcon getResizedImageIcon(final String file_name) {
        return new ImageIcon(
                new ImageIcon("src/main/resources/" + file_name + ".png")
                        .getImage()
                        .getScaledInstance(45, 45, Image.SCALE_DEFAULT)
        );
    }

    public GridBagConstraints defineDefaultConstraint(int x, int y) {

        GridBagConstraints rule = new GridBagConstraints();
        rule.gridx = x;
        rule.gridy = y;
        rule.gridwidth = 1;
        rule.gridheight = 1;
        rule.weightx = 0;
        rule.weighty = 0;

        rule.anchor = GridBagConstraints.CENTER;
        rule.fill = GridBagConstraints.NONE;
        rule.insets = new Insets(5, 5, 5, 5);
        rule.ipadx = 0;
        rule.ipady = 0;

        return rule;
    }
}