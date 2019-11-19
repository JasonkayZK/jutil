package top.jasonkayzk.jutil;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;

import static top.jasonkayzk.jutil.constant.RegPatterns.FILE_NAME_PATTERN;

/**
 * @author zk
 */
public class Formatter {

    /**
     * 从文件路径中获取文件名
     *
     * @param string 文件路径
     *
     * @return {@link String}
     */
    public static String getFileName(String string) {
        if (Checker.isNotEmpty(string)) {
            string = URLDecoder.decode(string, StandardCharsets.UTF_8);
            Matcher matcher = FILE_NAME_PATTERN.matcher(string);
            if (matcher.find() && Checker.isNotEmpty(matcher.group(0))) {
                String name = matcher.group(0).split("\\?")[0];
                if (Checker.isNotEmpty(name)) {
                    return name;
                }
            }
        }
        return "undefined";
    }
}
