package monapplication.components;

import javax.swing.*;
import java.awt.*;

public class ShopPanel extends JPanel {
    public ShopPanel(){
        setLayout(new GridLayout(0,3,10,10));
    }

    public void addItem(ShopItemPanel shopItemPanel){
        add(shopItemPanel);
    }
}
