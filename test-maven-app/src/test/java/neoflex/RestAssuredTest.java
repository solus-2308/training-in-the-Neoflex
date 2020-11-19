package neoflex;

import java.util.*;
import org.testng.annotations.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;


class RestAssuredTest{

    private static final Logger logger = LogManager.getLogger();

    @BeforeTest
    public void prepare(){
        try{
            ConfigReader conf = new ConfigReader();
            baseURI = conf.getValue("baseURI");
        }
        catch(Exception e){
            logger.info("Exception: " + e);
        }
    }

    public Response getResponse(String forGet){
        Response response =
            when().get(forGet).
            then().statusCode(200).extract().response();
        return response;
    }

    @Test
    public void testCase1(){

        logger.info("1)Список сущностей:");
        LinkedHashMap<String, String> map = getResponse("").path("");
        for(String field: map.keySet()){
            logger.info(field);
        }
    }

    @Test
    public void testCase2(){

        logger.info("2)Список фильмов");
        ArrayList<String> list = getResponse("/films").path("results.title");
        for(String field: list){
            logger.info(field);
        }
        logger.info("Информация по первому вышедшему фильму:");
        LinkedHashMap<String, String> map = getResponse("/films/1").path("");
        for(String field: map.keySet()){
            logger.info(field +": " + String.valueOf(map.get(field)));
        }
    }

    @Test
    public void testCase3(){

        logger.info("3)Список планет");
        ArrayList<String> list = getResponse("/planets").path("results.name");
        for(String field: list){
            logger.info(field);
        }
        logger.info("Информация по Татуину:");
        LinkedHashMap<String, String> map = getResponse("/planets/1").path("");
        for(String field: map.keySet()){
            logger.info(field +": " + String.valueOf(map.get(field)));
        }
    }

    @Test
    public void testCase4(){

        logger.info("4)Список рас");
        ArrayList<String> list = getResponse("/species").path("results.name");
        for(String field: list){
            logger.info(field);
        }
        logger.info("Информация про расу Вуки с Кашиика:");
        LinkedHashMap<String, String> map = getResponse("/species/3").path("");
        for(String field: map.keySet()){
            logger.info(field +": " + String.valueOf(map.get(field)));
        }
    }

    @Test
    public void testCase5(){

        logger.info("5)Пилоты X-wing-а");
        ArrayList<String> list = getResponse("/starships/12").path("pilots");
        for(String field: list){
            String answer = getResponse(field).path("name");
            logger.info(answer);
        }
    }
}