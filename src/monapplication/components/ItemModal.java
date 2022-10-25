package monapplication.components;


import magasin.exceptions.ArticleHorsStockException;
import magasin.exceptions.ClientInconnuException;
import magasin.exceptions.QuantiteEnStockInsuffisanteException;
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

public class ItemModal extends JDialog implements ActionListener {
    private final JLabel image;
    private final JLabel name;
    private final JLabel effect;
    private final JLabel price;
    private final JSpinner quantity;
    private final MainPanel context;

    private final JLabel placeholder = new JLabel("Hors Stock");
    private final JButton submit;
    private PokemonArticle article;

    public ItemModal(MainPanel context) {
        super(MonApplication.frame(), true);
        this.context = context;

        image = new JLabel();
        name = new JLabel();
        effect = new JLabel();
        price = new JLabel();
        quantity = new JSpinner();

        setLayout(new BorderLayout());

        JPanel infos = new JPanel(new BorderLayout());
        infos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JPanel infoFooter = new JPanel(new GridLayout(1, 3, 20, 30));

        submit = new JButton();
        submit.setText("Validate");
        submit.addActionListener(this);

        JPanel pricePanel = new JPanel();
        pricePanel.add(price);
        pricePanel.add(new JLabel(MonApplication.pokedollar()));

        JPanel qtPanel = new JPanel();
        qtPanel.add(quantity);
        qtPanel.add(placeholder);

        infoFooter.add(pricePanel);
        infoFooter.add(qtPanel);
        infoFooter.add(submit);
        infoFooter.setAlignmentY(Component.CENTER_ALIGNMENT);

        infos.add(name, BorderLayout.NORTH);
        infos.add(effect, BorderLayout.CENTER);
        infos.add(infoFooter, BorderLayout.SOUTH);

        add(image, BorderLayout.WEST);
        add(infos, BorderLayout.CENTER);

        setPreferredSize(new Dimension(550, 250));
    }

    public void update(PokemonArticle givenArticle) {
        this.article = givenArticle;
        try {
            image.setText(null);
            image.setIcon(new ImageIcon(ImageIO.read(new URL(article.spriteUrl())).getScaledInstance(150, 150, 1)));
        } catch (IOException e) {
            image.setIcon(null);
            image.setText(article.nom() + "-placeholder");
        }
        name.setText(article.nom());
        effect.setText("<html>" + article.effect().replaceFirst("\n:", " :\n").replaceAll("\n", "<br>") + "</html>");
        price.setText(String.valueOf(article.prix()));
        try {
            quantity.setVisible(true);
            placeholder.setVisible(false);
            submit.setEnabled(true);
            quantity.setModel(new SpinnerNumberModel(1, 0, MonApplication.magasin().consulterQuantiteEnStock(article), 1));
        } catch (ArticleHorsStockException | IllegalArgumentException e) {
            quantity.setVisible(false);
            placeholder.setVisible(true);
            submit.setEnabled(false);
        }
        pack();
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if ((int) (quantity.getValue()) != 0) {
                MonApplication.magasin().ajouterAuPanier(context.client(), article, (int) (quantity.getValue()));
            }
        } catch (ClientInconnuException | QuantiteNegativeOuNulleException | ArticleHorsStockException |
                 QuantiteEnStockInsuffisanteException ex) {
            throw new RuntimeException(ex);
        }
        MonApplication.adminPanel().updateStock();
        context.cartPanel().update();
        this.setVisible(false);
    }
}
