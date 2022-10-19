package monapplication.components;

import magasin.exceptions.ArticleHorsStockException;
import magasin.iClient;
import mesproduits.PokemonArticle.PokemonArticle;
import monapplication.MonApplication;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;

public class MainPanel extends JPanel{

    private ShopPanel shopPanel;
    private CartPanel cartPanel;

    private iClient client;
    public MainPanel(iClient client){
        this.client = client;
        setLayout(new BorderLayout());
        initBody();
    }

    private void initBody() {
        ScrollPane shopContainer = new ScrollPane();
        initShopContainer(shopContainer);

        cartPanel = new CartPanel();
        initCartContainer();

        add(shopContainer, BorderLayout.CENTER);
        add(cartPanel, BorderLayout.EAST);
    }

    private void initShopContainer(ScrollPane shopContainer) {
        shopPanel = new ShopPanel();
        shopContainer.add(shopPanel);
    }

    private void initCartContainer() {
        cartPanel.setPreferredSize(new Dimension(300,0));
    }

    public void addItem(PokemonArticle article){
        ShopItemPanel item = new ShopItemPanel(article);

        item.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                final JDialog frame = new JDialog(MonApplication.frame(), article.nom(), true);

                JPanel content = new JPanel(new BorderLayout());

                JLabel image;
                try {
                    Image img = ImageIO.read(new URL(article.spriteUrl())).getScaledInstance(150, 150, Image.SCALE_DEFAULT);
                    image = new JLabel(new ImageIcon(img), SwingConstants.CENTER);
                } catch (IOException ignored) {
                    image = new JLabel(article.nom() +"-placeHolder");
                }
                JPanel center = new JPanel(new BorderLayout());
                JLabel name = new JLabel("Name : " + article.nom());
                name.setPreferredSize(new Dimension(0,40));

                JLabel effect = new JLabel("<html>"+ article.effect().replaceFirst("\n:", " :\n").replaceAll("\n", "<br>")+"</html>");
                effect.setPreferredSize(new Dimension(0,100));
                JPanel logic = new JPanel();
                JLabel price = new JLabel("price : " + article.prix());

                JSpinner quantity = null;
                JLabel placeholder = null;
                try {
                    int qt = MonApplication.magasin().consulterQuantiteEnStock(article);
                    SpinnerModel model = new SpinnerNumberModel(1, 0, qt, 1);
                    quantity = new JSpinner(model);
                } catch (ArticleHorsStockException ex) {
                    placeholder = new JLabel("Hors Stock");
                }


                logic.add(price,RIGHT_ALIGNMENT);

                logic.add(quantity == null ?placeholder : quantity,CENTER_ALIGNMENT);
                logic.setPreferredSize(new Dimension(0,40));

                center.add(name, BorderLayout.NORTH);
                center.add(effect, BorderLayout.CENTER);
                center.add(logic, BorderLayout.SOUTH);

                content.add(image, BorderLayout.WEST);
                content.add(center,BorderLayout.CENTER);

                content.setPreferredSize(new Dimension(500,250));
                frame.add(content);
                frame.pack();
                frame.setVisible(true);
                frame.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        super.keyTyped(e);
                        if (e.getKeyCode()==KeyEvent.VK_ENTER){
                            frame.dispose();
                        }
                    }
                });
            }
        });
        shopPanel.addItem(item);
    }

    public ShopPanel shopPanel(){
        return shopPanel;
    }

}
