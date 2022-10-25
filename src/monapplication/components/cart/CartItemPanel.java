package monapplication.components.cart;

import com.formdev.flatlaf.FlatClientProperties;
import com.sun.tools.javac.Main;
import magasin.exceptions.*;
import mesproduits.PokemonArticle.PokemonArticle;
import monapplication.MonApplication;
import monapplication.components.MainPanel;

import javax.imageio.ImageIO;
import javax.naming.Context;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

public class CartItemPanel extends JPanel implements ActionListener {
    private final PokemonArticle article;
    private final int qt;
    private final MainPanel context;
    public CartItemPanel(PokemonArticle givenArticle, int quantity, MainPanel context) {
        this.context = context;
        article = givenArticle;
        qt = quantity;
        putClientProperty(FlatClientProperties.STYLE,
                "background: tint(@background,50%);" +
                        "border: 16,16,16,16,shade(@background,10%),,8");
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(210, 90));
        JLabel image;
        try {
            Image img = ImageIO.read(new URL(article.spriteUrl())).getScaledInstance(80, 80, Image.SCALE_DEFAULT);
            image = new JLabel(new ImageIcon(img), SwingConstants.CENTER);
        } catch (IOException e) {
            image = new JLabel(article.nom() + "-placeHolder");
        }

        JPanel text = new JPanel(new GridLayout(2, 1));
        text.setOpaque(false);
        JLabel name = new JLabel(article.nom());

        JPanel pricePanel = new JPanel(new GridLayout(1, 2));
        pricePanel.setOpaque(false);
        JPanel pricetext = new JPanel();
        pricetext.setOpaque(false);
        JLabel price = new JLabel(String.valueOf(article.prix()));
        JLabel unit = new JLabel(MonApplication.pokedollar());
        pricetext.add(price);
        pricetext.add(unit);

        JLabel qt = new JLabel("Qt = " + quantity);
        pricePanel.add(pricetext);
        pricePanel.add(qt);
        text.add(name, LEFT_ALIGNMENT);
        text.add(pricePanel, RIGHT_ALIGNMENT);

        JButton binButton = new JButton(MonApplication.binIcon());
        binButton.addActionListener(this);

        add(image, BorderLayout.WEST);
        add(text, BorderLayout.CENTER);
        add(binButton, BorderLayout.EAST);
    }

    public PokemonArticle article() {
        return article;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            MonApplication.magasin().supprimerDuPanier(context.client(),qt, article);
        } catch (ClientInconnuException | QuantiteNegativeOuNulleException | QuantiteSuppPanierException |
                 ArticleHorsPanierException | ArticleHorsStockException ex) {
            throw new RuntimeException(ex);
        }
        context.cartPanel().update();
    }
}
