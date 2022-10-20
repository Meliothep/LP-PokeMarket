package monapplication.components;

import magasin.exceptions.ArticleHorsStockException;
import magasin.exceptions.ClientInconnuException;
import magasin.exceptions.QuantiteEnStockInsuffisanteException;
import magasin.exceptions.QuantiteNegativeOuNulleException;
import magasin.iClient;
import mesproduits.PokemonArticle.PokemonArticle;
import monapplication.MonApplication;

import javax.swing.*;
import java.awt.*;

public class CartPanel extends JPanel {
    private JPanel mainList;
    private MainPanel context;

    public CartPanel(MainPanel context){
        this.context = context;
        setLayout(new BorderLayout());

        mainList = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.weighty = 1;
        mainList.add(new JPanel(), gbc);
        ScrollPane cartContainer = new ScrollPane();
        cartContainer.add(mainList);
        add(cartContainer, BorderLayout.CENTER);
    }

    private void addItem(PokemonArticle article, int quantity) {
        try {
            MonApplication.magasin().ajouterAuPanier(context.client(), article, quantity);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.weightx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            mainList.add(new CartItemPanel(article, quantity), gbc, 0);
        } catch (ClientInconnuException | QuantiteNegativeOuNulleException | ArticleHorsStockException |
                 QuantiteEnStockInsuffisanteException e) {
            throw new RuntimeException(e);
        }
    }

    public void update() {
        mainList.removeAll();
        try {
            MonApplication.magasin().consulterPanier(context.client()).listerCommande();
        } catch (ClientInconnuException e) {
            throw new RuntimeException(e);
        }

    }
}
