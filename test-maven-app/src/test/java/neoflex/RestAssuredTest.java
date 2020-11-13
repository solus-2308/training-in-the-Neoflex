package neoflex;

import java.util.*;
import org.testng.annotations.*;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;


class RestAssuredTest{

    @BeforeTest
    public void prepare(){
        baseURI = "https://swapi.dev/api";
    }

    @Test
    public void testCase1(){

        System.out.println("1)Список сущностей:");
        LinkedHashMap<String, String> map = get("").path("");
        for(String field: map.keySet()){
            System.out.println(field);
        }
    }

    @Test
    public void testCase2(){

        System.out.println("\n2)Список фильмов");
        ArrayList<String> list = get("/films").path("results.title");
        for(String field: list){
            System.out.println(field);
        }
        System.out.println("\nИнформация по первому вышедшему фильму:");
        LinkedHashMap<String, String> map = get("/films/1").path("");
        for(String field: map.keySet()){
            System.out.println(field +": " + String.valueOf(map.get(field)));
        }
    }

    @Test
    public void testCase3(){

        System.out.println("\n3)Список планет");
        ArrayList<String> list = get("/planets").path("results.name");
        for(String field: list){
            System.out.println(field);
        }
        System.out.println("\nИнформация по Татуину:");
        LinkedHashMap<String, String> map = get("/planets/1").path("");
        for(String field: map.keySet()){
            System.out.println(field +": " + String.valueOf(map.get(field)));
        }
    }

    @Test
    public void testCase4(){

        System.out.println("\n4)Список рас");
        ArrayList<String> list = get("/species").path("results.name");
        for(String field: list){
            System.out.println(field);
        }
        System.out.println("\nИнформация про расу Вуки с Кашиика:");
        LinkedHashMap<String, String> map = get("/species/3").path("");
        for(String field: map.keySet()){
            System.out.println(field +": " + String.valueOf(map.get(field)));
        }
    }

    @Test
    public void testCase5(){

        System.out.println("\n5)Пилоты X-wing-а");
        ArrayList<String> list = get("/starships/12").path("pilots");
        for(String field: list){
            String answer = get(field).path("name");
            System.out.println(answer);
        }
        System.out.println();
    }
}