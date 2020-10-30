package neoflex;

import java.util.Calendar;
import java.util.GregorianCalendar;

class TestContent {
    private String username = "Romka";
    private String city = "Penza";
    private Calendar calendar = new GregorianCalendar();
    
    String getUsername(){ return username; };
    String getCity(){ return city; }
    Calendar getCalendar() {return calendar;}
}
