package monapplication.views;

import javax.swing.*;
import java.awt.*;

public class ShopPannel extends JPanel {

    public  ShopPannel(){
        setLayout(new GridLayout(0,3));
    }

    public void addItem(ShopItemPannel shopItemPannel) {
        add(shopItemPannel);
        validate();
        repaint();
    }
}
