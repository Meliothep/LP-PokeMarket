package monapplication;

import mesproduits.*;
import monapplication.views.MainView;
import monapplication.views.ShopItemPannel;

import javax.swing.*;
import java.awt.*;

public class MonApplication {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Hello World Java Swing");

        // set frame site
        frame.setPreferredSize(new Dimension(1000, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        init(frame);

        // display it
        frame.pack();
        frame.setVisible(true);
    }

    private static void init(JFrame frame){
        MainView mainView = new MainView();
        for (int i = 1; i < 15*3; i++) {
            try {
                mainView.addShopItem(new ShopItemPannel(PokemonFactory.getPokemon(i)));
            } catch (PokemonFactoryException e) {
                throw new RuntimeException(e);
            }
            /**int finalI = i;
            Thread newThread = new Thread(()->{
                try {
                    mainView.addShopItem(new ShopItemPannel(PokemonFactory.getPokemon(finalI)));
                } catch (PokemonFactoryException e) {
                    throw new RuntimeException(e);
                }
            });
            newThread.start();**/
        }
        frame.add(mainView);
    }
}
