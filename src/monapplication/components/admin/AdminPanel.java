package monapplication.components.admin;

import magasin.iArticle;
import mesproduits.PokemonArticle.PokemonArticle;
import monapplication.MonApplication;

import javax.swing.*;
import java.awt.*;

public class AdminPanel extends JPanel {

    private final JPanel mainList;

    public AdminPanel() {
        setLayout(new BorderLayout());

        mainList = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.weighty = 1;
        mainList.add(new JPanel(), gbc);
        ScrollPane itemListContainer = new ScrollPane();
        itemListContainer.add(mainList);

        add(itemListContainer, BorderLayout.CENTER);
        init();
    }

    private void init() {
        for (iArticle article : MonApplication.magasin().listerArticlesEnStockParReference()) {
            addItem((PokemonArticle) article);
        }
        mainList.updateUI();
    }

    public void updateStock() {
        for (Component component : mainList.getComponents()) {
            if (component instanceof AdminItem) {
                ((AdminItem) component).update();
            }
        }
    }

    private void addItem(PokemonArticle article) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainList.add(new AdminItem(article), gbc, mainList.getComponentCount());
    }
}
