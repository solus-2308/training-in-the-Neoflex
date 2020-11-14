package neoflex;

import java.util.*;
import org.testng.annotations.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;


class RestAssuredTest{

    private static final Logger logger = LogManager.getLogger();

    @BeforeTest
    public void prepare(){
        baseURI = "https://swapi.dev/api";
    }

    @Test
    public void testCase1(){

        logger.info("1)Список сущностей:");
        LinkedHashMap<String, String> map = 
            when().get("").
            then().statusCode(200).extract().response().path("");
        for(String field: map.keySet()){
            logger.info(field);
        }
    }

    @Test
    public void testCase2(){

        logger.info("2)Список фильмов");
        ArrayList<String> list = 
            when().get("/films").
            then().statusCode(200).extract().response().path("results.title");
        for(String field: list){
            logger.info(field);
        }
        logger.info("Информация по первому вышедшему фильму:");
        LinkedHashMap<String, String> map = 
            when().get("/films/1").
            then().statusCode(200).extract().response().path("");
        for(String field: map.keySet()){
            logger.info(field +": " + String.valueOf(map.get(field)));
        }
    }

    @Test
    public void testCase3(){

        logger.info("3)Список планет");
        ArrayList<String> list = 
            when().get("/planets").
            then().statusCode(200).extract().response().path("results.name");
        for(String field: list){
            logger.info(field);
        }
        logger.info("Информация по Татуину:");
        LinkedHashMap<String, String> map = 
            when().get("/planets/1").
            then().statusCode(200).extract().response().path("");
        for(String field: map.keySet()){
            logger.info(field +": " + String.valueOf(map.get(field)));
        }
    }

    @Test
    public void testCase4(){

        logger.info("4)Список рас");
        ArrayList<String> list = 
            when().get("/species").
            then().statusCode(200).extract().response().path("results.name");
        for(String field: list){
            logger.info(field);
        }
        logger.info("Информация про расу Вуки с Кашиика:");
        LinkedHashMap<String, String> map = 
            when().get("/species/3").
            then().statusCode(200).extract().response().path("");
        for(String field: map.keySet()){
            logger.info(field +": " + String.valueOf(map.get(field)));
        }
    }

    @Test
    public void testCase5(){

        logger.info("5)Пилоты X-wing-а");
        ArrayList<String> list = 
            when().get("/starships/12").
            then().statusCode(200).extract().response().path("pilots");
        for(String field: list){
            String answer = 
                when().get(field).
                then().statusCode(200).extract().response().path("name");
            logger.info(answer);
        }
    }
}