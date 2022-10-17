package mesproduits;

import kong.unirest.json.JSONArray;
import magasin.iArticle;

public class PokemonArticle implements iArticle {

    private int id;
    private String name;
    private String spriteUrl;
    private int price;

    public PokemonArticle(int id, String name, String spriteUrl, int price){
        this.id = id;
        this.name = name;
        this.spriteUrl = spriteUrl;
        this.price = price;
    }
    @Override
    public int reference() {
        return id;
    }

    @Override
    public String nom() {return name;}

    @Override
    public double prix() {return price;}
}
