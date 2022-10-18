package monapplication.views;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class MainView extends JPanel{
    private JPanel header;
    private JPanel body;
    private CartPannel cart;
    private ShopPannel shop;
    public MainView(){
        this.setLayout(new BorderLayout());

        header = new JPanel(new BorderLayout());
        body = new JPanel(new BorderLayout());

        buildHeader();
        buildBody();

        add(header, BorderLayout.NORTH);
        add(body, BorderLayout.CENTER);
    }

    private void buildHeader() {
        JLabel logoLabel;
        try {
            BufferedImage bi = ImageIO.read(new URL("https://pbs.twimg.com/media/FAkrke7XoAIbof0.png"));
            Image logo = bi.getScaledInstance(70,70,Image.SCALE_DEFAULT);
            logoLabel = new JLabel(new ImageIcon(logo));
        } catch (IOException e) {
            logoLabel = new JLabel("Logo-placeHolder");
        }
        header.add(logoLabel, BorderLayout.WEST);
    }

    private void buildBody(){
        shop = new ShopPannel();
        body.add(new JScrollPane(shop), BorderLayout.CENTER);

        cart = new CartPannel();
        cart.setPreferredSize(new Dimension(300,0));
        body.add(new JScrollPane(cart), BorderLayout.EAST);
    }


    public void addShopItem(ShopItemPannel shopListPannel){
        shop.addItem(shopListPannel);
    }
}
