package monapplication.components.shop;

import com.formdev.flatlaf.FlatClientProperties;
import mesproduits.PokemonArticle.PokemonArticle;
import monapplication.MonApplication;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class ShopItemPanel extends JPanel {
    private final PokemonArticle article;

    public ShopItemPanel(PokemonArticle givenArticle) {
        article = givenArticle;
        putClientProperty(FlatClientProperties.STYLE,
                "background: tint(@background,50%);" +
                        "border: 16,16,16,16,shade(@background,10%),,8");
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(200, 230));
        JLabel image;
        try {

            Image img = ImageIO.read(new URL(article.spriteUrl())).getScaledInstance(150, 150, Image.SCALE_DEFAULT);
            image = new JLabel(new ImageIcon(img), SwingConstants.CENTER);
        } catch (IOException e) {
            image = new JLabel(article.nom() + "-placeHolder");
        }

        JPanel footer = new JPanel(new BorderLayout());
        footer.setOpaque(false);
        
        JLabel name = new JLabel(article.nom());

        JPanel price = new JPanel();
        price.setOpaque(false);
        JLabel pricetext = new JLabel(String.valueOf(article.prix()));
        price.add(pricetext);

        price.add(new JLabel(MonApplication.pokedollar()));
        footer.add(name, BorderLayout.WEST);
        footer.add(price, BorderLayout.EAST);


        add(image, BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH);
    }

    public PokemonArticle article() {
        return article;
    }
}
