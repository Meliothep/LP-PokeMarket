package monapplication.components;

import magasin.Commande;
import magasin.exceptions.ClientInconnuException;
import magasin.iArticle;
import mesproduits.PokemonArticle.PokemonArticle;
import monapplication.MonApplication;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class HistoryModal extends JDialog {

    private MainPanel context;
    private JTabbedPane tabbedPane;

    public HistoryModal(MainPanel context){
        super(MonApplication.frame(), true);
        this.context = context;
        tabbedPane = new JTabbedPane();
        add(tabbedPane);
        setPreferredSize(new Dimension(500,500));
    }

    private JPanel createCommandPannel(Commande commande){
        return new JPanel();
    }

    public void update() {
        try {
            tabbedPane.removeAll();
            int count = 1;
            for(Commande commande : MonApplication.magasin().listerCommandesTerminees(context.client())){
                System.out.println(count);
                tabbedPane.addTab("order " + count,createCommandPannel(commande));
                count ++;
            }
            pack();
            setVisible(true);
        } catch (ClientInconnuException e) {
            throw new RuntimeException(e);
        }
    }
}

