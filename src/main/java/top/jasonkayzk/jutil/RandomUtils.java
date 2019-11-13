package top.jasonkayzk.jutil;

import java.util.Random;

/**
 * @author zk
 */
public class RandomUtils extends org.apache.commons.lang3.RandomUtils {

    private RandomUtils() {}

    /**
     * 获取随机整数
     *
     * @param floor 下限
     * @param ceil 上限
     *
     * @return {@link Integer}
     */
    public static int getRandomInteger(int floor, int ceil) {
        return floor + new Random().nextInt(ceil - floor);
    }

    /**
     * 根据整数长度获取随机数
     *
     * @param length 随机数的整数长度
     *
     * @return {@link Integer}
     */
    public static int getRandomInteger(int length) {
        return getRandomInteger((int) Math.pow(10, length - 1), (int) Math.pow(10, length));
    }



}
