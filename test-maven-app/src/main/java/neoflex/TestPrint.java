package neoflex;
import java.util.Calendar;

class TestPrint {
    
    private TestContent data;

    TestPrint(TestContent data) {
        this.data = data;
    }

    void toPrint() {  
        Calendar calendar = data.getCalendar();
        String day = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
        String month = Integer.toString(calendar.get(Calendar.MONTH)+1);
        String year = Integer.toString(calendar.get(Calendar.YEAR));
        String date = day+"."+month+"."+year;

        System.out.println(data.getUsername()+" "+data.getCity()+" "+date);
    }
}
