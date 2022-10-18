package monapplication.views;

import mesproduits.PokemonArticle;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class ShopItemPannel extends JPanel {

    public ShopItemPannel(PokemonArticle article){
        add(createListItem(article));
    }

    private JPanel createListItem(PokemonArticle article){
        JPanel itemPannel = new JPanel(new BorderLayout());
        itemPannel.setPreferredSize(new Dimension(110,110));
        JLabel imageLabel;
        try {
            BufferedImage bi = ImageIO.read(new URL(article.spriteUrl()));
            Image image = bi.getScaledInstance(100,100,Image.SCALE_DEFAULT);
            imageLabel = new JLabel(new ImageIcon(image));
        } catch (IOException e) {
            imageLabel = new JLabel(article.nom() + "-placeHolder");
        }

        JLabel nameLabel = new JLabel(article.nom());
        nameLabel.setVerticalAlignment(SwingConstants.CENTER);

        JLabel priceLabel = new JLabel(String.valueOf(article.prix()));
        priceLabel.setVerticalAlignment(SwingConstants.CENTER);

        itemPannel.add(imageLabel, BorderLayout.CENTER);
        itemPannel.add(nameLabel, BorderLayout.NORTH);
        itemPannel.add(priceLabel, BorderLayout.SOUTH);

        return itemPannel;
    }
}
