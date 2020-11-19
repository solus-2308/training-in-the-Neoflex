package neoflex;

import java.util.Calendar;
import java.util.GregorianCalendar;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class TestContent {

    private String username;
    private String city;
    private Calendar calendar = new GregorianCalendar();
    private static final Logger logger = LogManager.getLogger();

    TestContent(){
        try{
            ConfigReader conf = new ConfigReader();
            username = conf.getValue("username");
            city = conf.getValue("city");
        }
        catch(Exception e){
            logger.info("Exception: " + e);
        }
    }
    
    String getUsername(){ return username; };
    String getCity(){ return city; }
    Calendar getCalendar() {return calendar;}
}
