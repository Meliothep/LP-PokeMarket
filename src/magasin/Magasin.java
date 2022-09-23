package magasin;

import magasin.exceptions.*;

import java.util.*;

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
        if(quantiteNouvelle < 0 ) throw new QuantiteNegativeException();
        if(stock.containsKey(nouvelArticle)) throw new ArticleDejaEnStockException();
        stock.put(nouvelArticle, quantiteNouvelle);
    }


    @Override
    public void reapprovisionnerStock(iArticle articleMaj, int quantiteAjoutee)
            throws ArticleHorsStockException, QuantiteNegativeOuNulleException {
        if(quantiteAjoutee <= 0) throw new QuantiteNegativeOuNulleException();
        if(!stock.containsKey(articleMaj)) throw new ArticleHorsStockException();
        stock.put(articleMaj,stock.get(articleMaj) + quantiteAjoutee);
    }

    @Override
    public int consulterQuantiteEnStock(iArticle articleRecherche) throws ArticleHorsStockException {
        if(!stock.containsKey(articleRecherche)) throw new ArticleHorsStockException();
        return stock.get(articleRecherche);
    }

    @Override
    public void retirerDuStock(int quantiteRetiree, iArticle articleMaj)
            throws ArticleHorsStockException, QuantiteNegativeOuNulleException, QuantiteEnStockInsuffisanteException {
        if(quantiteRetiree<=0) throw new QuantiteNegativeOuNulleException();
        if(!stock.containsKey(articleMaj)) throw new ArticleHorsStockException();
        if(quantiteRetiree>stock.get(articleMaj))throw new QuantiteEnStockInsuffisanteException();

        stock.put(articleMaj, stock.get(articleMaj)-quantiteRetiree);
    }


    @Override
    public List<iArticle> listerArticlesEnStockParNom() {
        List<iArticle> articlesordreAlpha = new ArrayList<>(stock.keySet());
        articlesordreAlpha.sort(iArticle.COMPARATEUR_NOM);
        return articlesordreAlpha;
    }

    @Override
    public List<iArticle> listerArticlesEnStockParReference() {
        List<iArticle> articlesParRef = new ArrayList<>(stock.keySet());
        articlesParRef.sort(iArticle.COMPARATEUR_REFERENCE);
        return articlesParRef;
    }

    @Override
    public List<Map.Entry<iArticle, Integer>> listerStock() {
        return stock.entrySet().stream().sorted((o1, o2) -> iArticle.COMPARATEUR_NOM.compare(o1.getKey(), o2.getKey())).toList();
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