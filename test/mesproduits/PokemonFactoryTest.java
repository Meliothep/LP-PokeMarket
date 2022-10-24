package mesproduits;

import kong.unirest.JsonNode;
import kong.unirest.json.JSONObject;
import mesproduits.PokemonArticle.PokemonArticle;
import mesproduits.PokemonArticle.PokemonFactory;
import mesproduits.PokemonArticle.PokemonFactoryException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class PokemonFactoryTest {
    @Test
    public void callApiTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String json = (String) getCallApiMethod().invoke(null, 1);
        JSONObject obj = new JsonNode(json).getObject();
        Assertions.assertTrue(obj.keySet().containsAll(Arrays.stream(new String[]{"id", "name", "sprites", "cost"}).collect(Collectors.toList())));
        obj = obj.getJSONObject("sprites");
        Assertions.assertTrue(obj.keySet().contains("default"));
    }

    @Test
    public void deserialyzeTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String json = "{\"cost\": 1, \"effect_entries\": [{\"effect\": \"effect\"}],\"id\": 2,\"name\": \"itemName\",\"sprites\": {\"default\": \"something.png\"}}";

        PokemonArticle article = (PokemonArticle) getDeserialyzeMethod().invoke(null, json);

        Assertions.assertEquals("itemName", article.nom());
        Assertions.assertEquals(2, article.reference());
        Assertions.assertEquals(1, article.prix());
        Assertions.assertEquals("effect", article.effect());
        Assertions.assertEquals("something.png", article.spriteUrl());
    }

    @Test
    public void when_deserialyze_throw_exception() throws NoSuchMethodException, IllegalAccessException {
        String json = "{\"name\": \"bulbasaur\", \"sprites\": { \"front_default\": \"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png\"},\"weight\": 69}";
        Exception exception = assertThrows(InvocationTargetException.class, () -> getDeserialyzeMethod().invoke(null, json));
        Assertions.assertEquals(PokemonFactoryException.class, exception.getCause().getClass());
    }

    private Method getCallApiMethod() throws NoSuchMethodException {
        Method method = PokemonFactory.class.getDeclaredMethod("callApi", int.class);
        method.setAccessible(true);
        return method;
    }

    private Method getDeserialyzeMethod() throws NoSuchMethodException {
        Method method = PokemonFactory.class.getDeclaredMethod("deserialyze", String.class);
        method.setAccessible(true);
        return method;
    }

}
