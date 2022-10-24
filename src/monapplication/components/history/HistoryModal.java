package monapplication.components.history;

import magasin.Commande;
import magasin.exceptions.ClientInconnuException;
import magasin.iArticle;
import mesproduits.PokemonArticle.PokemonArticle;
import monapplication.MonApplication;
import monapplication.components.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class HistoryModal extends JDialog {

    private final MainPanel context;
    private final JTabbedPane tabbedPane;

    public HistoryModal(MainPanel context) {
        super(MonApplication.frame(), true);
        this.context = context;
        tabbedPane = new JTabbedPane();
        add(tabbedPane);
        setPreferredSize(new Dimension(350, 500));
        setResizable(false);
    }

    private ScrollPane createCommandPannel(Commande commande) {
        JPanel mainList = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.weighty = 1;
        mainList.add(new JPanel(), gbc);
        ScrollPane commandContainer = new ScrollPane();
        commandContainer.add(mainList);
        for (Map.Entry<iArticle, Integer> entry : commande.listerCommande()) {
            addItem(mainList, entry);
        }
        return commandContainer;
    }

    private void addItem(JPanel mainList, Map.Entry<iArticle, Integer> entry) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainList.add(new HistoryItemPannel((PokemonArticle) entry.getKey(), entry.getValue()), gbc, 0);
    }

    public void update() {
        try {
            tabbedPane.removeAll();
            int count = 1;
            for (Commande commande : MonApplication.magasin().listerCommandesTerminees(context.client())) {
                tabbedPane.addTab("order " + count, createCommandPannel(commande));
                count++;
            }
            pack();
            setVisible(true);
        } catch (ClientInconnuException e) {
            throw new RuntimeException(e);
        }
    }
}

