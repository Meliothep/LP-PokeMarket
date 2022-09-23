package magasin;

import magasin.exceptions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Magasin implements iStock, iClientele, iPanier{

    // istock
    Map<iArticle, Integer> stock;
    // iClientele
    List<iClient> clientele;
    // iPanier
    Map<iClient, Commande> paniers;
    Map<iClient, List<Commande>> historique;

    public Magasin() {
        stock = new HashMap<>();
        clientele = new ArrayList<>();
        paniers = new HashMap<>();
        historique = new HashMap<>();
    }


    // iStock

    @Override
    public void referencerAuStock(iArticle nouvelArticle, int quantiteNouvelle)
            throws ArticleDejaEnStockException, QuantiteNegativeException {
        // TODO
    }


    @Override
    public void reapprovisionnerStock(iArticle articleMaj, int quantiteAjoutee)
            throws ArticleHorsStockException, QuantiteNegativeOuNulleException {
        // TODO
    }

    @Override
    public int consulterQuantiteEnStock(iArticle articleRecherche) throws ArticleHorsStockException {
        // TODO
        return -1;
    }

    @Override
    public void retirerDuStock(int quantiteRetiree, iArticle articleMaj)
            throws ArticleHorsStockException, QuantiteNegativeOuNulleException, QuantiteEnStockInsuffisanteException {
        // TODO
    }


    @Override
    public List<iArticle> listerArticlesEnStockParNom() {
        // TODO
        return null;
    }

    @Override
    public List<iArticle> listerArticlesEnStockParReference() {
        // TODO
        return null;
    }

    @Override
    public List<Map.Entry<iArticle, Integer>> listerStock() {
        // TODO
        return null;
    }

    // iClientele


    @Override
    public void enregistrerNouveauClient(iClient nouveauClient) throws ClientDejaEnregistreException {
        if (clientele.contains(nouveauClient))
            throw new ClientDejaEnregistreException();
        clientele.add(nouveauClient);
    }

    @Override
    public List<iClient> listerLesClientsParId() {
        clientele.sort(iClient.COMPARATEUR_ID);
        return clientele;
    }


    // iPanier

    @Override
    public Commande consulterPanier(iClient client) throws ClientInconnuException {
        if(!paniers.containsKey(client))
            throw new ClientInconnuException();
        return paniers.get(client);
    }

    @Override
    public void ajouterAuPanier(iClient client, iArticle article, int quantite)
            throws ClientInconnuException,
            QuantiteNegativeOuNulleException,
            ArticleHorsStockException, QuantiteEnStockInsuffisanteException {
        if(!paniers.containsKey(client))
            throw new ClientInconnuException();
        retirerDuStock(quantite, article);
        paniers.get(client).ajout(quantite, article);
    }

    @Override
    public void supprimerDuPanier(iClient client, int quantite, iArticle article)
            throws ClientInconnuException,
            QuantiteNegativeOuNulleException,
            QuantiteSuppPanierException, ArticleHorsPanierException,
            ArticleHorsStockException {
        if (!paniers.containsKey(client))
            throw new ClientInconnuException();
        reapprovisionnerStock(article, quantite);
        paniers.get(client).retirer(quantite,article);

    }

    @Override
    public double consulterMontantPanier(iClient client) throws ClientInconnuException {
        if (!paniers.containsKey(client))
            throw new ClientInconnuException();
        return paniers.get(client).montant();
    }

    @Override
    public void viderPanier(iClient client) throws ClientInconnuException {
        if (!paniers.containsKey(client))
            throw new ClientInconnuException();
        for (Map.Entry<iArticle, Integer> articleEntry : paniers.get(client).listerCommande()) {
            try {
                reapprovisionnerStock(articleEntry.getKey(), articleEntry.getValue());
                paniers.get(client).retirer(articleEntry.getValue(), articleEntry.getKey());
            }catch (Exception ignored){}
        }
    }

    @Override
    public void terminerLaCommande(iClient client) throws ClientInconnuException {
        if (!paniers.containsKey(client))
            throw new ClientInconnuException();
        if (!historique.containsKey(client))
            historique.put(client, new ArrayList<>());
        historique.get(client).add(paniers.get(client));
        paniers.put(client, new Commande());
    }

    @Override
    public List<Commande> listerCommandesTerminees(iClient client) throws ClientInconnuException {
        if (!historique.containsKey(client))
            throw new ClientInconnuException();
        return historique.get(client);
    }

    @Override
    public double consulterMontantTotalCommandes(iClient client) throws ClientInconnuException {
        if (!historique.containsKey(client))
            throw new ClientInconnuException();
        return historique.get(client).stream().mapToDouble(Commande::montant).sum();
    }


}