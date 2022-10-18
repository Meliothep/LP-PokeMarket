package mesproduits;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONException;
import kong.unirest.json.JSONObject;

public class PokemonFactory {

    private static int idcount = 0;
    public static PokemonArticle getPokemon(int id) throws PokemonFactoryException {
        String json = callApi(id);
        return deserialyze(json);
    }

    private static PokemonArticle deserialyze(String json) throws PokemonFactoryException {
        JSONObject obj = new JsonNode(json).getObject();
        try {
            int code = obj.getInt("id");
            String name = obj.getString("name");
            String spriteUrl = obj.getJSONObject("sprites").getString("default");
            int price = obj.getInt("cost");
            return new PokemonArticle(getId(), code, name, spriteUrl, price);
        }catch (JSONException e){
            throw new PokemonFactoryException();
        }
    }
    private static String callApi(int id){
        HttpResponse<JsonNode> response = Unirest.get("https://pokeapi.co/api/v2/item/" + id)
                .header("accept", "application/json")
                .asJson();
        return response.getBody().getArray().get(0).toString();
    }

    private static int getId(){
        int id = idcount;
        idcount ++;
        return id;
    }

}
