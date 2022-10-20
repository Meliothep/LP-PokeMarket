package monapplication.components;

import mesproduits.PokemonArticle.PokemonArticle;
import monapplication.MonApplication;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class CartItemPanel extends JPanel {
    private final PokemonArticle article;

    public PokemonArticle article(){ return article; }

    public CartItemPanel(PokemonArticle givenArticle, int quantity) {
        article = givenArticle;

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(200,90));
        JLabel image;
        try {
            Image img = ImageIO.read(new URL(article.spriteUrl())).getScaledInstance(80, 80, Image.SCALE_DEFAULT);
            image = new JLabel(new ImageIcon(img), SwingConstants.CENTER);
        } catch (IOException e) {
            image = new JLabel(article.nom() +"-placeHolder");
        }

        JPanel text = new JPanel(new GridLayout(2,1));

        JLabel name = new JLabel(article.nom());

        JPanel pricePanel = new JPanel(new GridLayout(1,2));

        JPanel pricetext = new JPanel();
        JLabel price = new JLabel(String.valueOf(article.prix()));
        JLabel unit =  new JLabel(MonApplication.pokedollar());
        pricetext.add(price);
        pricetext.add(unit);

        JLabel qt = new JLabel("Qt = " + quantity);
        pricePanel.add(pricetext);
        pricePanel.add(qt);
        text.add(name, LEFT_ALIGNMENT);
        text.add(pricePanel,RIGHT_ALIGNMENT);

        add(image,BorderLayout.WEST);
        add(text,BorderLayout.CENTER);
    }
}
