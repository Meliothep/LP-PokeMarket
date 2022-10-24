package monapplication.components.admin;

import magasin.exceptions.ClientInconnuException;
import magasin.iClient;
import monapplication.MonApplication;

import javax.swing.*;
import java.awt.*;

public class ClientItem extends JPanel {
    public ClientItem(iClient client) throws ClientInconnuException {
        setLayout(new GridLayout(2,1,10,10));
        JLabel clientIdentity = new JLabel(client.id());
        JLabel clientHistory = new JLabel("Commandes passées : "+
                String.valueOf(MonApplication.magasin().listerCommandesTerminees(client).size()));
        add(clientIdentity);
        add(clientHistory);
        setVisible(true);
    }
}
