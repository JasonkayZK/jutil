package top.jasonkayzk;

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





}
