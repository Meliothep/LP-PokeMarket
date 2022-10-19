package monapplication.components;

import mesproduits.PokemonArticle.PokemonArticle;
import monapplication.MonApplication;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;

public class ShopItemPanel extends JPanel {
    private PokemonArticle article;

    public PokemonArticle article(){ return article;}
    public ShopItemPanel(PokemonArticle givenArticle) {
        article = givenArticle;
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(200,230));
        JLabel image;
        try {

            Image img = ImageIO.read(new URL(article.spriteUrl())).getScaledInstance(150, 150, Image.SCALE_DEFAULT);
            image = new JLabel(new ImageIcon(img), SwingConstants.CENTER);
        } catch (IOException e) {
            image = new JLabel(article.nom() +"-placeHolder");
        }

        JPanel footer = new JPanel();
        JLabel name = new JLabel(article.nom());
        JLabel empty = new JLabel("     ");
        JLabel price = new JLabel(String.valueOf(article.prix()));
        footer.add(name);
        footer.add(empty);
        footer.add(price);

        footer.add(new JLabel(MonApplication.pokedollar()));

        add(image,BorderLayout.CENTER);
        add(footer,BorderLayout.SOUTH);
    }
}
