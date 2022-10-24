package monapplication.components.shop;

import javax.swing.*;
import java.awt.*;

public class ShopPanel extends JPanel {
    public ShopPanel() {
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setLayout(new GridLayout(0, 3, 10, 10));
    }

    public void addItem(ShopItemPanel shopItemPanel) {
        add(shopItemPanel);
    }
}
