package monapplication.components;

import javax.swing.*;
import java.awt.*;

public class CartPanel extends JPanel {

    private JPanel mainList;

    public CartPanel(){
        setLayout(new BorderLayout());

        mainList = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.weighty = 1;
        mainList.add(new JPanel(), gbc);

    }
}
