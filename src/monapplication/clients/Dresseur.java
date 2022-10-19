package monapplication.clients;

import magasin.iClient;

public class Dresseur implements iClient {

    private static int count = 0;

    private int id;
    private String name;

    public Dresseur(){
        this.name = "Dresseur";
        this.id = count;
        count++;
    }
    @Override
    public String id() {
        return name + id;
    }

    @Override
    public int compareTo(iClient o) {
        return iClient.COMPARATEUR_ID.compare(this, o);
    }
}
