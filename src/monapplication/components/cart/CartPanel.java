package monapplication.components.cart;

import magasin.exceptions.ClientInconnuException;
import magasin.iArticle;
import mesproduits.PokemonArticle.PokemonArticle;
import monapplication.MonApplication;
import monapplication.components.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class CartPanel extends JPanel implements ActionListener {
    private final JPanel mainList;

    private final JButton buyButton;
    private final MainPanel context;
    private final JLabel globalPrice;
    private JButton history;

    public CartPanel(MainPanel context) {
        this.context = context;
        setLayout(new BorderLayout());

        JPanel header = new JPanel(new GridLayout(1, 2, 10, 50));
        JLabel title = new JLabel("Cart");
        history = null;

        history = new JButton("History");
        history.setEnabled(false);
        history.addActionListener(this);
        JPanel titlePan = new JPanel();
        titlePan.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        titlePan.add(title);
        header.add(titlePan);
        header.add(history, CENTER_ALIGNMENT);
        header.setPreferredSize(new Dimension(0, 50));
        add(header, BorderLayout.NORTH);

        mainList = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.weighty = 1;
        mainList.add(new JPanel(), gbc);
        ScrollPane cartContainer = new ScrollPane();
        cartContainer.add(mainList);
        add(cartContainer, BorderLayout.CENTER);

        JPanel footer = new JPanel(new GridLayout(1, 2, 30, 0));

        buyButton = new JButton();
        buyButton.setText("Validate");
        buyButton.setEnabled(false);
        buyButton.addActionListener(this);

        JPanel globalPricePanel = new JPanel();
        globalPricePanel.setOpaque(false);
        globalPricePanel.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));
        globalPrice = new JLabel("Total : 0.0");
        JLabel unit = new JLabel(MonApplication.pokedollar());

        globalPricePanel.add(globalPrice);
        globalPricePanel.add(unit);

        footer.add(globalPricePanel);
        footer.add(buyButton, CENTER_ALIGNMENT);
        footer.setPreferredSize(new Dimension(0, 50));

        add(footer, BorderLayout.SOUTH);

    }

    private void addItem(PokemonArticle article, int quantity) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainList.add(new CartItemPanel(article, quantity, context), gbc, 0);
    }

    public void update() {

        mainList.removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.weighty = 1;
        mainList.add(new JPanel(), gbc);
        try {
            if (MonApplication.magasin().consulterPanier(context.client()).estVide()) {
                buyButton.setEnabled(false);
            }else {
                buyButton.setEnabled(true);
                for (Map.Entry<iArticle, Integer> map : MonApplication.magasin().consulterPanier(context.client()).listerCommande()) {
                    addItem((PokemonArticle) map.getKey(), map.getValue());
                }
            }
            mainList.updateUI();
            globalPrice.updateUI();
            globalPrice.setText("Total : " + MonApplication.magasin().consulterPanier(context.client()).montant());

        } catch (ClientInconnuException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        if (source.getText() == "Validate") {
            buyButton.setEnabled(false);
            try {
                MonApplication.magasin().terminerLaCommande(context.client());
            } catch (ClientInconnuException ex) {
                throw new RuntimeException(ex);
            }
            history.setEnabled(true);
            mainList.removeAll();
            mainList.updateUI();
            globalPrice.setText("Total : 0.0");
            update();
        } else {
            context.showClientHistoy();
        }
    }
}
