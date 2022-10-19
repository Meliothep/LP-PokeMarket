package mesproduits.PokemonArticle;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONException;
import kong.unirest.json.JSONObject;

public class PokemonFactory {

    public static PokemonArticle getPokemon(int id) throws PokemonFactoryException {
        String json = callApi(id);
        return deserialyze(json);
    }

    private static PokemonArticle deserialyze(String json) throws PokemonFactoryException {
        JSONObject obj = new JsonNode(json).getObject();
        try {
            int id = obj.getInt("id");
            String name = obj.getString("name");
            String spriteUrl = obj.getJSONObject("sprites").getString("default");
            int price = obj.getInt("cost");
            String effect = obj.getJSONArray("effect_entries").getJSONObject(0).getString("effect");
            return new PokemonArticle(id, name, spriteUrl, price, effect);
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
}
