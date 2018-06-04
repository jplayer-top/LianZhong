package top.jplayer.baseprolibrary;

import org.junit.Test;

import java.util.Calendar;
import java.util.Locale;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        long a = 1518172027989L;
        long l = a / 10000 * 10000 + 10000L;
        System.out.println(String.valueOf(l));
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;
        String monthStr = month > 9 ? month + "" : "0" + month;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String dayStr = day > 9 ? day + "" : "0" + day;
        String format = String.format(Locale.CHINA, "%s-%s 10:00", monthStr, dayStr);
        System.out.print(format);
    }


}