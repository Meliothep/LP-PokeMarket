package mesproduits;

import magasin.iArticle;

public class PokemonArticle implements iArticle {

    private int id;
    private int code;
    private String name;
    private String spriteUrl;
    private int price;

    public PokemonArticle(int id, int code,String name, String spriteUrl, int price){
        this.id = id;
        this.code = code;
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

    public String spriteUrl() {return spriteUrl;}

    public int ref() {return code;}
}
