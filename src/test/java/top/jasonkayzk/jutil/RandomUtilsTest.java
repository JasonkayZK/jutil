package top.jasonkayzk.jutil;

import org.junit.Test;
import top.jasonkayzk.jutil.RandomUtils;

/**
 * @author zk
 */
public class RandomUtilsTest {

    @Test
    public void testRandomIntegerByRange() {
        for (int i = 0; i < 100; ++i) {
            System.out.println(RandomUtils.getRandomInteger(0, 1000));
        }
    }

    @Test
    public void testRandomIntegerByLength() {
        for (int i = 0; i < 10; ++i) {
            System.out.println(RandomUtils.getRandomInteger(i));
        }
    }

    @Test
    public void testGetRandomString() {
        for (int i = 0; i < 10; i++) {
            System.out.println(RandomUtils.getRandomString(i));
        }
    }

    @Test
    public void testGetRandomText() {
        for (int i = 0; i < 10; i++) {
            System.out.println(RandomUtils.getRandomText(32, 126, i));
        }
    }

    @Test
    public void getRandomIntegerIgnoreRange() {
        for (int i = 0; i < 10; i++) {
            System.out.println(RandomUtils.getRandomIntegerIgnoreRange(32, 126, new int[] {32, 100}, new int[] {102,126}));
        }
    }



}
