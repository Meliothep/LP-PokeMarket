package mesproduits;

import kong.unirest.JsonNode;
import kong.unirest.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PokemonFactoryTest {
    @Test
    public void callApiTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String json = (String) getCallApiMethod().invoke(null, 1);
        JSONObject obj = new JsonNode(json).getObject();
        Assertions.assertTrue(obj.keySet().containsAll(Arrays.stream(new String[]{"id", "name", "sprites", "cost"}).toList()));
        obj = obj.getJSONObject("sprites");
        Assertions.assertTrue(obj.keySet().contains("default"));
    }

    @Test
    public void deserialyzeTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String json = "{\"name\": \"premier-ball\", \"id\": \"12\", \"sprites\": { \"default\": \"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/premier-ball.png\"},\"cost\": 20}";
        PokemonArticle article = (PokemonArticle) getDeserialyzeMethod().invoke(null,json);

        Assertions.assertEquals("premier-ball", article.nom());
        Assertions.assertEquals(0,article.reference());
        Assertions.assertEquals(12, article.ref());
        Assertions.assertEquals(20, article.prix());
        Assertions.assertEquals("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/premier-ball.png", article.spriteUrl());
    }

    @Test
    public void when_deserialyze_throw_exception() throws NoSuchMethodException, IllegalAccessException {
        String json = "{\"name\": \"bulbasaur\", \"sprites\": { \"front_default\": \"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png\"},\"weight\": 69}";
        Exception exception = assertThrows(InvocationTargetException.class, () -> getDeserialyzeMethod().invoke(null,json));
        Assertions.assertEquals(PokemonFactoryException.class,exception.getCause().getClass());
    }

    @Test
    public void getIdTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        int base = (int) getGetId().invoke(null);
        assertEquals(base+1,getGetId().invoke(null));
    }

    private Method getCallApiMethod() throws NoSuchMethodException{
        Method method = PokemonFactory.class.getDeclaredMethod("callApi", int.class);
        method.setAccessible(true);
        return method;
    }

    private Method getDeserialyzeMethod() throws NoSuchMethodException{
        Method method = PokemonFactory.class.getDeclaredMethod("deserialyze", String.class);
        method.setAccessible(true);
        return method;
    }

    private Method getGetId() throws NoSuchMethodException{
        Method method = PokemonFactory.class.getDeclaredMethod("getId");
        method.setAccessible(true);
        return method;
    }
}
