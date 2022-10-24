package monapplication.components.admin;

import magasin.exceptions.ArticleHorsStockException;
import magasin.exceptions.ClientInconnuException;
import magasin.iArticle;
import magasin.iClient;
import mesproduits.PokemonArticle.PokemonArticle;
import monapplication.MonApplication;
import monapplication.components.CartItemPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class AdminPanel extends JPanel {

    JPanel stock;
    JPanel clientActions;
    JPanel allClients;
    JPanel onSaitPasTropEncoreQuoiMettreDedansMaisTktOnGere;

    public AdminPanel(){
        setLayout(new BorderLayout());

        clientActions = new JPanel(new BorderLayout());
        allClients = new JPanel(new GridLayout(0,1, 10, 10));
        onSaitPasTropEncoreQuoiMettreDedansMaisTktOnGere = new JPanel();

        stock = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.weighty = 1;
        stock.add(new JPanel(), gbc);

        JScrollPane scroll = new JScrollPane();
        scroll.add(stock);

        for(Map.Entry<iArticle, Integer> poke : MonApplication.magasin().listerStock()){
            addItem((PokemonArticle) poke.getKey());
        }

        for(iClient client : MonApplication.magasin().listerLesClientsParId()){
            ClientItem toAdminClientPanel;
            try {
                toAdminClientPanel = new ClientItem(client);
            } catch (ClientInconnuException e) {
                throw new RuntimeException(e);
            }
            allClients.add(toAdminClientPanel);
        }
        allClients.setBorder(BorderFactory.createLineBorder(Color.black));

        onSaitPasTropEncoreQuoiMettreDedansMaisTktOnGere.add(new JLabel("TEST"));
        onSaitPasTropEncoreQuoiMettreDedansMaisTktOnGere.setBorder(BorderFactory.createLineBorder(Color.black));

        clientActions.add(allClients, BorderLayout.NORTH);
        clientActions.add(onSaitPasTropEncoreQuoiMettreDedansMaisTktOnGere, BorderLayout.CENTER);



        add(scroll, BorderLayout.CENTER);
        add(clientActions, BorderLayout.EAST);
        setVisible(true);


    }
    private void addItem(PokemonArticle pokemon){
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.weightx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
        try {
            JPanel test = new JPanel();
            test.setBorder(BorderFactory.createLineBorder(Color.black));
            stock.add(new AdminItem(pokemon), gbc, 0);

            //stock.add(, gbc, 0);
        } catch (ArticleHorsStockException e) {
            throw new RuntimeException(e);
        }


    }


}
