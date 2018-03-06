package top.jplayer.baseprolibrary;

import org.junit.Test;

import static org.junit.Assert.*;

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

    }
}