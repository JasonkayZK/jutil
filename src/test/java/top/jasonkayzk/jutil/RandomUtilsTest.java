package top.jasonkayzk.jutil;

import org.junit.Test;
import top.jasonkayzk.jutil.RandomUtils;

/**
 * @author zk
 */
public class RandomUtilsTest {

    @Test
    public void testGetRandomInteger() {
        for (int i = 0; i < 10; i++) {
            System.out.println(RandomUtils.getRandomInteger());
        }
    }

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

    @Test
    public void getRandomStringWithoutSymbol() {
        for (int i = 0; i < 10; i++) {
            System.out.println(RandomUtils.getRandomStringWithoutSymbol(i));
        }

    }

    @Test
    public void getRandomTextIgnoreRange() {
        for (int i = 0; i < 10; i++) {
            System.out.println(RandomUtils.getRandomTextIgnoreRange(32, 122, i, new int[] {32, 44}));
        }
    }

    @Test
    public void getRandomStringOnlyLetter() {
        for (int i = 0; i < 10; i++) {
            System.out.println(RandomUtils.getRandomStringOnlyLetter(i));
        }
    }

    @Test
    public void getRandomStringOnlyLowerCase() {
        for (int i = 0; i < 10; i++) {
            System.out.println(RandomUtils.getRandomStringOnlyLowerCase(i));
        }
    }

    @Test
    public void getRandomStringOnlyUpperCase() {
        for (int i = 0; i < 10; i++) {
            System.out.println(RandomUtils.getRandomStringOnlyUpperCase(i));
        }
    }

    @Test
    public void getRandomNumber() {
        for (int i = 0; i < 10; i++) {
            System.out.println(RandomUtils.getRandomNumber(i));
        }
    }

    @Test
    public void getRandomDouble() {
        for (int i = 0; i < 10; i++) {
            System.out.println(RandomUtils.getRandomDouble());
        }
    }

    @Test
    public void testGetRandomDouble() {
        for (int i = 0; i < 10; i++) {
            System.out.println(RandomUtils.getRandomDouble(-5.0, 10.5));
        }
    }

    @Test
    public void testGetRandomDouble1() {
        for (int i = 0; i < 10; i++) {
            System.out.println(RandomUtils.getRandomDouble(-5.0, 10.5, 4));
        }
    }

    @Test
    public void getRandomColor() {
        for (int i = 0; i < 10; i++) {
            System.out.println(RandomUtils.getRandomColor(0.5));
        }
    }

    @Test
    public void testGetRandomColor() {
        for (int i = 0; i < 10; i++) {
            System.out.println(RandomUtils.getRandomColor());
        }
    }
}
