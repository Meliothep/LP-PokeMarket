package monapplication;

import magasin.Magasin;
import magasin.exceptions.ArticleDejaEnStockException;
import magasin.exceptions.ClientDejaEnregistreException;
import magasin.exceptions.QuantiteNegativeException;
import magasin.iArticle;
import mesproduits.PokemonArticle.PokemonArticle;
import mesproduits.PokemonArticle.PokemonFactory;
import mesproduits.PokemonArticle.PokemonFactoryException;
import monapplication.clients.Dresseur;
import monapplication.components.MainPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class MonApplication {
    private static JFrame frame;
    private static ImageIcon icon;
    private static Magasin magasin;
    private static int tabCount = 1;
    private static JTabbedPane tabbedPane;
    private static ImageIcon pokedollar;

    public static void main(String[] args) {
        JWindow window = new JWindow();
        window.getContentPane().add(new JLabel("Loading data from API", SwingConstants.CENTER));
        window.setBounds(500, 150, 300, 200);
        window.setVisible(true);

        init();

        window.setVisible(false);
        frame.setVisible(true);
    }

    private static void init(){
        magasin = new Magasin();
        populate();

        frame = new JFrame("PokeMart Application");

        try {
            icon = new ImageIcon(
                    ImageIO.read(new URL("https://pbs.twimg.com/media/FAkrke7XoAIbof0.png")).getScaledInstance(20, 20, Image.SCALE_DEFAULT));
            pokedollar = new ImageIcon(ImageIO.read(new URL("https://static.miraheze.org/pokeclickerwiki/8/89/PokeCoin.png")).getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // set frame site
        frame.setPreferredSize(new Dimension(1000, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        tabbedPane = new JTabbedPane();

        addTab();
        addTab();
        addTab();
        frame.add(tabbedPane,BorderLayout.CENTER);


        // display it
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private static void addAdminTab(){
        /*
        final JDialog frame = new JDialog(parentFrame, frameTitle, true);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
        */
    }

    private static void addTab(){
        Dresseur client = new Dresseur();
        try {
            magasin.enregistrerNouveauClient(client);
        } catch (ClientDejaEnregistreException e) { System.out.println("Look like an invalid client"); }

        MainPanel mainPanel = new MainPanel(client);
        tabbedPane.addTab(client.id(), icon, mainPanel, client.id() + "'s tab");
        tabCount++;
        for(iArticle item : magasin.listerArticlesEnStockParReference()){
            PokemonArticle article = (PokemonArticle) item;
            mainPanel.addItem(article);
        }
    }

    private static void populate() {
        for (int i = 1; i < 10*3+1; i++) {
            try {
                PokemonArticle article = PokemonFactory.getPokemon(i);
                magasin.referencerAuStock(article, (int)(Math.random()*15)+1);
            } catch (PokemonFactoryException | QuantiteNegativeException | ArticleDejaEnStockException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static ImageIcon pokedollar(){ return pokedollar; }

    public static JFrame frame(){ return frame; }

    public static Magasin magasin(){ return magasin; }

}