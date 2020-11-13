package neoflex;

import static io.restassured.RestAssured.*;
import java.util.*;
import org.testng.annotations.*;


class RestAssured{
    @Test
    public void test(){

        System.out.println("1)Список сущностей:");
        LinkedHashMap<String, String> map = get("https://swapi.dev/api").path("");
        for(String field: map.keySet()){
            System.out.println(field);
        }

        System.out.println("\n2)Список фильмов");
        ArrayList<String> list = get("https://swapi.dev/api/films").path("results.title");
        for(String field: list){
            System.out.println(field);
        }
        System.out.println("\nИнформация по первому вышедшему фильму:");
        map = get("https://swapi.dev/api/films/1").path("");
        for(String field: map.keySet()){
            System.out.println(field +": " + String.valueOf(map.get(field)));
        }

        System.out.println("\n3)Список планет");
        list = get("https://swapi.dev/api/planets").path("results.name");
        for(String field: list){
            System.out.println(field);
        }
        System.out.println("\nИнформация по Татуину:");
        map = get("https://swapi.dev/api/planets/1").path("");
        for(String field: map.keySet()){
            System.out.println(field +": " + String.valueOf(map.get(field)));
        }

        System.out.println("\n4)Список рас");
        list = get("https://swapi.dev/api/species").path("results.name");
        for(String field: list){
            System.out.println(field);
        }
        System.out.println("\nИнформация про расу Вуки с Кашиика:");
        map = get("https://swapi.dev/api/species/3").path("");
        for(String field: map.keySet()){
            System.out.println(field +": " + String.valueOf(map.get(field)));
        }

        System.out.println("\n5)Пилоты X-wing-а");
        list = get("https://swapi.dev/api/starships/12").path("pilots");
        for(String field: list){
            String answer = get(field).path("name");
            System.out.println(answer);
        }
        System.out.println();
    }
}