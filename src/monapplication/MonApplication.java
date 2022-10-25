package monapplication;

import com.formdev.flatlaf.FlatLightLaf;
import magasin.Magasin;
import magasin.exceptions.ArticleDejaEnStockException;
import magasin.exceptions.ClientDejaEnregistreException;
import magasin.exceptions.QuantiteNegativeException;
import magasin.iArticle;
import mesproduits.PokemonArticle.PokemonArticle;
import mesproduits.PokemonArticle.PokemonFactory;
import mesproduits.PokemonArticle.PokemonFactoryException;
import monapplication.components.MainPanel;
import monapplication.components.admin.AdminPanel;
import monapplication.customers.Dresseur;

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
    private static ImageIcon adminIcon;
    private static ImageIcon binIcon;

    private static AdminPanel adminPanel;

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }

        JWindow window = new JWindow();

        window.getContentPane().add(new JLabel("Loading data from API", SwingConstants.CENTER));
        window.setBounds(500, 150, 300, 200);
        window.setVisible(true);

        init();

        window.setVisible(false);
        frame.setVisible(true);
    }

    private static void init() {
        magasin = new Magasin();
        populate();

        frame = new JFrame("PokeMart Application");

        try {
            icon = new ImageIcon(
                    ImageIO.read(new URL("https://pbs.twimg.com/media/FAkrke7XoAIbof0.png")).getScaledInstance(20, 20, Image.SCALE_DEFAULT));
            adminIcon = new ImageIcon(
                    ImageIO.read(new URL("https://www.clipartmax.com/png/full/153-1530219_team-rocket-clipart-pokemon-team-rocket-logo.png")).getScaledInstance(20, 20, Image.SCALE_DEFAULT));
            pokedollar = new ImageIcon(ImageIO.read(new URL("https://static.miraheze.org/pokeclickerwiki/8/89/PokeCoin.png")).getScaledInstance(20, 20, Image.SCALE_DEFAULT));
            binIcon = new ImageIcon(ImageIO.read(new URL("https://cdn-icons-png.flaticon.com/512/54/54324.png")).getScaledInstance(40, 40, Image.SCALE_DEFAULT));
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
        addAdminTab();
        frame.add(tabbedPane, BorderLayout.CENTER);


        // display it
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private static void addAdminTab() {
        adminPanel = new AdminPanel();
        tabbedPane.addTab("Admin", adminIcon, adminPanel, "Administrator's tab");
    }

    private static void addTab() {
        Dresseur client = new Dresseur();
        try {
            magasin.enregistrerNouveauClient(client);
        } catch (ClientDejaEnregistreException e) {
            System.out.println("Look like an invalid client");
        }

        MainPanel mainPanel = new MainPanel(client);
        tabbedPane.addTab(client.id(), icon, mainPanel, client.id() + "'s tab");
        tabCount++;
        for (iArticle item : magasin.listerArticlesEnStockParReference()) {
            PokemonArticle article = (PokemonArticle) item;
            mainPanel.addItem(article);
        }
    }

    private static void populate() {
        for (int i = 1; i < 10 * 3 + 1; i++) {
            try {
                PokemonArticle article = PokemonFactory.getPokemon(i);
                magasin.referencerAuStock(article, (int) (Math.random() * 15) + 1);
            } catch (PokemonFactoryException | QuantiteNegativeException | ArticleDejaEnStockException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static ImageIcon pokedollar() {
        return pokedollar;
    }

    public static ImageIcon binIcon() {
        return binIcon;
    }

    public static JFrame frame() {
        return frame;
    }

    public static AdminPanel adminPanel() {
        return adminPanel;
    }

    public static Magasin magasin() {
        return magasin;
    }

}