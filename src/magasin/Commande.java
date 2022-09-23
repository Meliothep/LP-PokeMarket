package magasin;

import magasin.exceptions.ArticleHorsPanierException;
import magasin.exceptions.QuantiteNegativeOuNulleException;
import magasin.exceptions.QuantiteSuppPanierException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * défini une commande, c'est-à-dire des articles associés à leur quantité commandée
 */

public class Commande implements Comparable<Commande> {

    Map<iArticle, Integer> articles;

    public Commande() {
        articles = new HashMap<>();
    }

    /**
     * indique si la commande est vide
     *
     * @return
     */
    public boolean estVide() {
        return articles.isEmpty();
    }

    /**
     * ajoute la quantité indiquée de l'article considéré  à la commande
     *
     * @param quantite        quantité à ajouter
     * @param articleCommande article à considérer
     * @throws QuantiteNegativeOuNulleException si la quantité indiquée est négative ou nulle
     */
    public void ajout(int quantite, iArticle articleCommande)
            throws QuantiteNegativeOuNulleException {
        if(quantite <= 0)
            throw new QuantiteNegativeOuNulleException();

        if(articles.containsKey(articleCommande)){
            articles.put(articleCommande, articles.get(articleCommande) + quantite);
        }else{
            articles.put(articleCommande, quantite);
        }

    }

    /**
     * retire de la commande la quantité indiquée de l'article considéré
     *
     * @param quantite        quantité à retirer
     * @param articleCommande article à considérer
     * @throws QuantiteNegativeOuNulleException si la quantité indiquée est négative ou nulle
     * @throws QuantiteSuppPanierException      si la quantité indiquée est supp à celle dans da commande
     * @throws ArticleHorsPanierException       si l'article considéré n'est pas dans la commande
     */
    public void retirer(int quantite, iArticle articleCommande)
            throws QuantiteNegativeOuNulleException,
            QuantiteSuppPanierException, ArticleHorsPanierException {
        if(quantite <= 0)
            throw new QuantiteNegativeOuNulleException();
        if(!articles.containsKey(articleCommande))
            throw new ArticleHorsPanierException();
        if(articles.get(articleCommande) < quantite)
            throw new QuantiteSuppPanierException();
        if (articles.get(articleCommande) == quantite) {
            articles.remove(articleCommande);
        } else {
            articles.put(articleCommande, articles.get(articleCommande) - quantite);
        }
    }

    /**
     * donne une liste de tous les articles présent dans la commande
     * (trié par nom d'article)
     *
     * @return
     */
    public List<iArticle> listerArticlesParNom() {
        return articles.keySet().stream().sorted(iArticle.COMPARATEUR_NOM).collect(Collectors.toList());
    }

    /**
     * donne une liste de tous les articles présent dans la commande
     * (trié par reference)
     *
     * @return
     */
    public List<iArticle> listerArticlesParReference() {
        return articles.keySet().stream().sorted(iArticle.COMPARATEUR_REFERENCE).collect(Collectors.toList());
    }

    /**
     * donne une liste de tous les couples (articleCommande, quantiteCommandee)
     * présent dans la commande
     *
     * @return
     */
    public List<Map.Entry<iArticle, Integer>> listerCommande() {
        //return new ArrayList<>(articles.entrySet());
        return articles.entrySet().stream().sorted(new Comparator<Map.Entry<iArticle, Integer>>() {
            @Override
            public int compare(Map.Entry<iArticle, Integer> o1, Map.Entry<iArticle, Integer> o2) {
                return o1.getValue().compareTo(o2.getValue()) != 0 ? o1.getValue().compareTo(o2.getValue()) : o1.getKey().nom().compareTo(o2.getKey().nom());
            }
        }).collect(Collectors.toList());
    }


    /**
     * donne la quantité commandée de l'article considéré
     *
     * @param article l'article à considérer
     * @throws ArticleHorsPanierException si l'article considéré n'est pas dans la commande
     * @return la quantité commmandée
     */
    public int quantite(iArticle article) throws ArticleHorsPanierException {
        if(!articles.containsKey(article))
            throw new ArticleHorsPanierException();
        return articles.get(article);
    }

    /**
     * donne le montant actuel de la commande
     *
     * @return
     */
    public double montant() {
        double montant = 0;
        for (Map.Entry<iArticle, Integer> articleEntry : articles.entrySet()) {
            montant += articleEntry.getKey().prix() * articleEntry.getValue();
        }
        return montant;
    }

    @Override
    public int compareTo(Commande o) {
        // TODO
        return 0;
    }
}
