package mesproduits.PokemonArticle;

import magasin.iArticle;

public class PokemonArticle implements iArticle {

    private int id;
    private String name;
    private String spriteUrl;

    private String effect;
    private int price;

    public PokemonArticle(int id,String name, String spriteUrl, int price, String effect){
        this.id = id;
        this.name = name;
        this.spriteUrl = spriteUrl;
        this.price = price;
        this.effect = effect;
    }
    @Override
    public int reference() {
        return id;
    }

    @Override
    public String nom() {return name;}

    @Override
    public double prix() {return price;}

    public String spriteUrl() {return spriteUrl;}

    public String effect() {return effect;}
}
