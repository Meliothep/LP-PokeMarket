package monapplication.components;

import magasin.iClient;
import mesproduits.PokemonArticle.PokemonArticle;
import monapplication.components.cart.CartPanel;
import monapplication.components.history.HistoryModal;
import monapplication.components.shop.ShopItemPanel;
import monapplication.components.shop.ShopPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainPanel extends JPanel {

    private final HistoryModal historyModal;
    private final ItemModal itemModal;
    private final iClient client;
    private ShopPanel shopPanel;
    private CartPanel cartPanel;

    public MainPanel(iClient client) {
        this.client = client;
        itemModal = new ItemModal(this);
        historyModal = new HistoryModal(this);
        setLayout(new BorderLayout());
        initBody();
    }

    private void initBody() {
        ScrollPane shopContainer = new ScrollPane();
        initShopContainer(shopContainer);

        cartPanel = new CartPanel(this);
        initCartContainer();

        add(shopContainer, BorderLayout.CENTER);
        add(cartPanel, BorderLayout.EAST);
    }

    private void initShopContainer(ScrollPane shopContainer) {
        shopPanel = new ShopPanel();
        shopContainer.add(shopPanel);
    }

    private void initCartContainer() {
        cartPanel.setPreferredSize(new Dimension(300, 0));
    }

    public void addItem(PokemonArticle article) {
        ShopItemPanel item = new ShopItemPanel(article);

        item.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                itemModal.update(article);
                item.setVisible(true);
            }
        });
        shopPanel.addItem(item);
    }

    public ShopPanel shopPanel() {
        return shopPanel;
    }

    public CartPanel cartPanel() {
        return cartPanel;
    }

    public iClient client() {
        return client;
    }

    public void showClientHistoy() {
        historyModal.update();
    }
}
