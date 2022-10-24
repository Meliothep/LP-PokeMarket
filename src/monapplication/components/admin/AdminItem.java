package monapplication.components.admin;

import magasin.exceptions.ArticleHorsStockException;
import magasin.exceptions.QuantiteNegativeOuNulleException;
import magasin.iArticle;
import mesproduits.PokemonArticle.PokemonArticle;
import monapplication.MonApplication;
import org.w3c.dom.events.Event;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class AdminItem extends JPanel {
    JButton reaprovisionner;
    JSpinner nombreAAdd;
    JLabel quantite;

    public AdminItem(PokemonArticle pokemonArticle) throws ArticleHorsStockException {
        quantite = new JLabel(String.valueOf(pokemonArticle.nom()));
        reaprovisionner = new JButton("Réaprovisionner");
        nombreAAdd = new JSpinner();

        JLabel nomArticle = new JLabel(String.valueOf(MonApplication.magasin().
                consulterQuantiteEnStock(pokemonArticle)));
        JLabel image = new JLabel(new ImageIcon(pokemonArticle.spriteUrl()));
        JPanel infosArticle = new JPanel(new BorderLayout());
        JPanel gestionArticles = new JPanel(new BorderLayout());
        JPanel reaprovisionnement = new JPanel(new BorderLayout());


        nombreAAdd.setModel(new SpinnerNumberModel(1, 1,100, 1));
        reaprovisionner.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    MonApplication.magasin().reapprovisionnerStock(pokemonArticle, (Integer) nombreAAdd.getValue());
                } catch (ArticleHorsStockException | QuantiteNegativeOuNulleException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        setLayout(new BorderLayout());

        infosArticle.add(nomArticle, BorderLayout.NORTH);

        reaprovisionnement.add(reaprovisionner, BorderLayout.WEST);
        reaprovisionnement.add(nombreAAdd, BorderLayout.EAST);

        gestionArticles.add(quantite, BorderLayout.WEST);
        gestionArticles.add(reaprovisionnement, BorderLayout.EAST);

        infosArticle.add(gestionArticles, BorderLayout.SOUTH);

        add(image, BorderLayout.WEST);
        add(infosArticle, BorderLayout.EAST);

        setPreferredSize(new Dimension(0,100));
    }
}
