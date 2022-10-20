package monapplication.components;

import magasin.exceptions.ArticleHorsStockException;
import magasin.exceptions.ClientInconnuException;
import magasin.exceptions.QuantiteEnStockInsuffisanteException;
import magasin.exceptions.QuantiteNegativeOuNulleException;
import magasin.iArticle;
import magasin.iClient;
import mesproduits.PokemonArticle.PokemonArticle;
import monapplication.MonApplication;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

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

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainList.add(new CartItemPanel(article, quantity), gbc, 0);

    }

    public void update() {
        mainList.removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.weighty = 1;
        mainList.add(new JPanel(), gbc);
        try {
            for(Map.Entry<iArticle, Integer> map : MonApplication.magasin().consulterPanier(context.client()).listerCommande()){
                addItem((PokemonArticle) map.getKey(),map.getValue());
            }
        } catch (ClientInconnuException e) {
            throw new RuntimeException(e);
        }

    }
}
