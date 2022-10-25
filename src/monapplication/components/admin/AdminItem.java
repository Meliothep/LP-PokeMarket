package monapplication.components.admin;

import com.formdev.flatlaf.FlatClientProperties;
import magasin.exceptions.ArticleHorsStockException;
import magasin.exceptions.QuantiteNegativeOuNulleException;
import mesproduits.PokemonArticle.PokemonArticle;
import monapplication.MonApplication;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

public class AdminItem extends JPanel implements ActionListener {
    private final JSpinner restockSpinner;
    private final PokemonArticle article;
    private JLabel quantite;

    public AdminItem(PokemonArticle pokemonArticle) {

        article = pokemonArticle;

        putClientProperty(FlatClientProperties.STYLE,
                "background: tint(@background,50%);" +
                        "border: 16,16,16,16,shade(@background,10%),,8");

        setLayout(new BorderLayout());

        JLabel image;
        try {
            Image img = ImageIO.read(new URL(pokemonArticle.spriteUrl())).getScaledInstance(80, 80, Image.SCALE_DEFAULT);
            image = new JLabel(new ImageIcon(img), SwingConstants.CENTER);
        } catch (IOException e) {
            image = new JLabel(pokemonArticle.nom() + "-placeHolder");
        }
        add(image, BorderLayout.WEST);

        JPanel infosArticle = new JPanel(new BorderLayout());
        infosArticle.setOpaque(false);

        JPanel namePanel = new JPanel(new BorderLayout());
        namePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        namePanel.setPreferredSize(new Dimension(150, 125));
        namePanel.setOpaque(false);

        JLabel articleName = new JLabel(pokemonArticle.nom());
        articleName.setPreferredSize(new Dimension(75, 50));
        articleName.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 25));
        try {
            quantite = new JLabel(String.valueOf(MonApplication.magasin().consulterQuantiteEnStock(pokemonArticle)));
        } catch (ArticleHorsStockException e) {
            quantite = new JLabel("Hors-Stock");
        }
        quantite.setPreferredSize(new Dimension(25, 25));

        namePanel.add(articleName, BorderLayout.CENTER);
        namePanel.add(quantite, BorderLayout.EAST);

        infosArticle.add(namePanel, BorderLayout.WEST);

        JLabel description = new JLabel("<html>" + pokemonArticle.effect().replaceFirst("\n:", " :\n").replaceAll("\n", "<br>") + "</html>");
        description.setPreferredSize(new Dimension(250, 100));

        infosArticle.add(description, BorderLayout.CENTER);

        JPanel restockPanel = new JPanel();
        restockPanel.setOpaque(false);

        restockPanel.setBorder(BorderFactory.createEmptyBorder(50, 10, 50, 10));

        JButton restockButton = new JButton("Réaprovisionner");
        restockSpinner = new JSpinner();

        restockSpinner.setModel(new SpinnerNumberModel(1, 1, 100, 1));
        restockButton.addActionListener(this);

        restockPanel.add(restockSpinner);
        restockPanel.add(restockButton);

        infosArticle.add(restockPanel, BorderLayout.EAST);

        add(infosArticle, BorderLayout.CENTER);

        setPreferredSize(new Dimension(0, 150));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            MonApplication.magasin().reapprovisionnerStock(article, (Integer) restockSpinner.getValue());
            quantite.setText(String.valueOf(MonApplication.magasin().consulterQuantiteEnStock(article)));
            updateUI();
        } catch (ArticleHorsStockException | QuantiteNegativeOuNulleException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void update() {
        try {
            quantite.setText(String.valueOf(MonApplication.magasin().consulterQuantiteEnStock(article)));
        } catch (ArticleHorsStockException e) {
            throw new RuntimeException(e);
        }
        updateUI();
    }
}
