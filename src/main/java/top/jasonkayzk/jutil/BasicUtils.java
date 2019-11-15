package top.jasonkayzk.jutil;

/**
 * @author zk
 */
public class BasicUtils {

    /**
     * 获取当前操作系统名称
     *
     * @return {@link String}
     */
    public static String getCurrentOS() {
        return System.getProperties().getProperty("os.name").toLowerCase();
    }

}
