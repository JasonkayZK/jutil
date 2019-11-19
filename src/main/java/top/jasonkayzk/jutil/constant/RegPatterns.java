package top.jasonkayzk.jutil.constant;

import java.util.regex.Pattern;

/**
 * 正则表达式Pattern常量类
 *
 * @author zk
 */
public class RegPatterns {

    /**
     * 超链接匹配，忽略大小写
     */
    public static final Pattern HYPER_LINK_PATTERN = Pattern.compile("^(https*://)?([^\\s&;\"':<>]+\\.)+[a-z0-9]+" +
        "(/[^\\s]*)*$", Pattern.CASE_INSENSITIVE);

    /**
     * 日期匹配
     */
    public static final Pattern DATE_PATTERN = Pattern.compile("^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$");

    /**
     * 整数匹配
     */
    public static final Pattern NUMBER_PATTERN = Pattern.compile("^[0-9]+$");

    /**
     * 数字匹配
     */
    public static final Pattern DECIMAL_PATTERN = Pattern.compile("^[0-9]+(\\.[0-9]+)?$");

    /**
     * 邮箱匹配，忽略大小写
     */
    public static final Pattern EMAIL_PATTERN = Pattern.compile("^[0-9a-z\\-]+([0-9a-z\\-]|(\\.[0-9a-z\\-]+))" +
        "*@[0-9a-z\\-]+(\\.[0-9a-z\\-]+)+$", Pattern.CASE_INSENSITIVE);

    /**
     * 图片匹配
     */
    public static final Pattern IMAGES_PATTERN = Pattern.compile(".*\\.(bmp|gif|jpe?g|png|tiff?|pcx|tga|svg|pic)$",
        Pattern.CASE_INSENSITIVE);

    /**
     * 文件名匹配（不能包含非法字符）,忽略大小写
     */
    public static final Pattern FILE_NAME_PATTERN = Pattern.compile("([^/\\\\:*\"<>|?]+\\.)*[^/\\\\:*\"<>|?]+(\\?.*)" +
        "?$", Pattern.CASE_INSENSITIVE);
}
