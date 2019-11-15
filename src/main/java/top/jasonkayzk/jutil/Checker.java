package top.jasonkayzk.jutil;

import cn.hutool.core.util.StrUtil;
import top.jasonkayzk.jutil.annotations.FieldChecker;
import top.jasonkayzk.jutil.interfaces.IChecker;
import top.jasonkayzk.jutil.model.CheckResult;
import top.jasonkayzk.jutil.model.ResultObject;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static top.jasonkayzk.jutil.constant.RegPatterns.*;

/**
 * @author zk
 */
public class Checker {

    private Checker() {}

    /**
     * 验证JavaBean带有 {@link FieldChecker}注解的字段
     *
     * @param bean JavaBean
     *
     * @return {@link CheckResult}
     *
     * @since 1.1.0
     */

    public static <T> CheckResult<T> checkBean(Object bean) {
        return checkBean(bean, new HashMap<>(2));
    }

    /**
     * 验证JavaBean带有 {@link FieldChecker}注解的字段
     *
     * @param bean JavaBean
     * @param map 对象映射
     *
     * @return {@link CheckResult}
     *
     * @since 1.1.0
     */
    public static <T> CheckResult<T> checkBean(Object bean, Map<String, Object> map) {
        CheckResult<T> result = new CheckResult<>();
        if (isNull(bean)) {
            result.passed = false;
            result.resultObject = CheckResult.getErrorResult();
            return result;
        }
        Field[] fields = bean.getClass().getDeclaredFields();
        ResultObject<T> object = new ResultObject<>();
        for (Field field : fields) {
            FieldChecker checking = field.getAnnotation(FieldChecker.class);
            if (isNotNull(checking)) {
                field.setAccessible(true);
                String expression = checking.expression();
                Object value;
                try {
                    value = field.get(bean);
                } catch (IllegalAccessException e) {
                    LoggerUtils.error("get value from field[{}] error: " + e.getMessage(), field.getName());
                    object.status = "Error";
                    object.message = "获取参数值失败";
                    object.code = 501;
                    result.passed = false;
                    break;
                }
                // 设置默认值
                if (isNull(value) && isNotEmpty(checking.defaultValue())) {
                    try {
                        field.set(bean, checking.defaultValue());
                        continue;
                    } catch (IllegalAccessException e) {
                        LoggerUtils.error("set default to field[{}] error: " + e.getMessage(), field.getName());
                    }
                }
                if (isEmpty(expression)) {
                    // 默认进行不为空验证
                    result.passed = isNotNull(value);
                    if (result.passed) {
                        if (field.getType() == Number.class) {
                            result.passed = (Double) value >= 0;
                        } else {
                            result.passed = isNotEmpty(value.toString());
                        }
                    }
                } else if (expression.startsWith(":")) {
                    // 正则匹配
                    result.passed = isNotNull(value);
                    if (result.passed) {
                        result.passed = Pattern.compile(expression.substring(1)).matcher(value.toString()).matches();
                    }
                } else {
                    // 自定义表达式验证
                    result.passed = true;
                    if (!expression.contains("null")) {
                        result.passed = isNotNull(value);
                    }
                    if (result.passed) {
                        map.put("val", value);
                        Object res = ReflectUtils.executeExpression(expression, map);
                        result.passed = res instanceof Boolean && (boolean) res;
                    }
                }
                if (!result.passed) {
                    object.code = checking.code();
                    object.message = StrUtil.format(checking.message(), field.getName());
                    object.status = checking.status();
                    break;
                }
            }
        }
        if (result.passed) {
            FieldChecker checking = bean.getClass().getAnnotation(FieldChecker.class);
            if (isNotNull(checking)) {
                object.code = checking.code();
                object.message = checking.message();
                object.status = checking.status();
            }
        }
        result.resultObject = object;
        return result;
    }

    /**
     * 对象是否为NULL
     *
     * @param object 需要判断的对象
     *
     * @return {@link Boolean}
     *
     * @since 1.1.0
     */
    public static boolean isNull(Object object) {
        return object == null;
    }

    /**
     * 对象是否不为NULL
     *
     * @param object 需要判断的对象
     *
     * @return {@link Boolean}
     *
     * @since 1.1.0
     */
    public static boolean isNotNull(Object object) {
        return !isNull(object);
    }

    /**
     * 检查Double是否为null
     *
     * @param value 值
     * @param elseValue 为null返回的值
     *
     * @return {@link Double}
     *
     * @since 1.1.0
     */
    public static Double checkNull(Double value, Double elseValue) {
        return isNull(value) ? elseValue : value;
    }

    /**
     * 检查Double是否为null
     *
     * @param value 值
     * @param elseValue 为null返回的值
     *
     * @return {@link Double}
     *
     * @since 1.1.0
     */
    public static Double checkNull(Double value, double elseValue) {
        return checkNull(value, Double.valueOf(elseValue));
    }

    /**
     * 检查Double是否为null
     *
     * @param value 值
     *
     * @return {@link Double}
     *
     * @since 1.1.0
     */
    public static Double checkNull(Double value) {
        return checkNull(value, 0);
    }

    /**
     * 检查Integer是否为null
     *
     * @param value 值
     * @param elseValue 为null返回的值
     *
     * @return {@link Integer}
     *
     * @since 1.1.0
     */
    public static Integer checkNull(Integer value, Integer elseValue) {
        return isNull(value) ? elseValue : value;
    }

    /**
     * 检查Integer是否为null
     *
     * @param value 值
     * @param elseValue 为null返回的值
     *
     * @return {@link Integer}
     *
     * @since 1.1.0
     */
    public static Integer checkNull(Integer value, int elseValue) {
        return checkNull(value, Integer.valueOf(elseValue));
    }

    /**
     * 检查Integer是否为null
     *
     * @param value 值
     *
     * @return {@link Integer}
     *
     * @since 1.1.0
     */
    public static Integer checkNull(Integer value) {
        return checkNull(value, 0);
    }

    /**
     * 检查Long是否为null
     *
     * @param value 值
     * @param elseValue 为null返回的值
     *
     * @return {@link Long}
     *
     * @since 1.1.0
     */
    public static Long checkNull(Long value, Long elseValue) {
        return isNull(value) ? elseValue : value;
    }

    /**
     * 检查Long是否为null
     *
     * @param value 值
     * @param elseValue 为null返回的值
     *
     * @return {@link Long}
     *
     * @since 1.1.0
     */
    public static Long checkNull(Long value, long elseValue) {
        return checkNull(value, Long.valueOf(elseValue));
    }

    /**
     * 检查Long是否为null
     *
     * @param value 值
     *
     * @return {@link Long}
     *
     * @since 1.1.0
     */
    public static Long checkNull(Long value) {
        return checkNull(value, 0);
    }

    /**
     * 检查Float是否为null
     *
     * @param value 值
     * @param elseValue 为null返回的值
     *
     * @return {@link Float}
     *
     * @since 1.1.0
     */
    public static Float checkNull(Float value, Float elseValue) {
        return isNull(value) ? elseValue : value;
    }

    /**
     * 检查Float是否为null
     *
     * @param value 值
     * @param elseValue 为null返回的值
     *
     * @return {@link Float}
     *
     * @since 1.1.0
     */
    public static Float checkNull(Float value, float elseValue) {
        return checkNull(value, Float.valueOf(elseValue));
    }

    /**
     * 检查Float是否为null
     *
     * @param value 值
     *
     * @return {@link Float}
     *
     * @since 1.1.0
     */
    public static Float checkNull(Float value) {
        return checkNull(value, 0);
    }

    /**
     * 检查Short是否为null
     *
     * @param value 值
     * @param elseValue 为null返回的值
     *
     * @return {@link Short}
     *
     * @since 1.1.0
     */
    public static Short checkNull(Short value, Short elseValue) {
        return isNull(value) ? elseValue : value;
    }

    /**
     * 检查Short是否为null
     *
     * @param value 值
     * @param elseValue 为null返回的值
     *
     * @return {@link Short}
     *
     * @since 1.1.0
     */
    public static Short checkNull(Short value, short elseValue) {
        return checkNull(value, Short.valueOf(elseValue));
    }

    /**
     * 检查Short是否为null
     *
     * @param value 值
     *
     * @return {@link Short}
     *
     * @since 1.1.0
     */
    public static Short checkNull(Short value) {
        short s = 0;
        return checkNull(value, s);
    }

    /**
     * 检查Character是否为null
     *
     * @param value 值
     * @param elseValue 为null返回的值
     *
     * @return {@link Character}
     *
     * @since 1.1.0
     */
    public static Character checkNull(Character value, Character elseValue) {
        return isNull(value) ? elseValue : value;
    }

    /**
     * 检查Character是否为null
     *
     * @param value 值
     * @param elseValue 为null返回的值
     *
     * @return {@link Character}
     *
     * @since 1.1.0
     */
    public static Character checkNull(Character value, char elseValue) {
        return checkNull(value, Character.valueOf(elseValue));
    }

    /**
     * 检查Character是否为null
     *
     * @param value 值
     *
     * @return {@link Character}
     *
     * @since 1.1.0
     */
    public static Character checkNull(Character value) {
        return checkNull(value, '0');
    }
    
    /**
     * 字符串是否为NULL或空
     *
     * @param string 需要判断的字符串
     *
     * @return {@link Boolean}
     *
     * @since 1.1.0
     */
    public static boolean isEmpty(String string) {
        return isNull(string) || string.isEmpty();
    }

    /**
     * 替换字符之前检查字符串是否为空
     *
     * @param string 需要检测的字符串
     * @param oldChar 需要替换的字符
     * @param newChar 新的字符
     *
     * @return {@link String}
     */
    public static String replace(String string, char oldChar, char newChar) {
        return checkNull(string).replace(oldChar, newChar);
    }

    /**
     * 替换字符串之前检查字符串是否为空
     *
     * @param string 需要检测的字符串
     * @param oldString 需要替换的字符串
     * @param newString 新的字符串
     *
     * @return {@link String}
     */
    public static String replace(String string, String oldString, String newString) {
        return checkNull(string).replace(oldString, newString);
    }

    /**
     * 字符串是否不为空
     *
     * @param string 需要判断的字符串
     *
     * @return {@link Boolean}
     *
     * @since 1.1.0
     */
    public static boolean isNotEmpty(String string) {
        return !isEmpty(string);
    }

    /**
     * 检查字符串是否等于某个字符串
     *
     * @param value 字符串
     *
     * @param values 待测数组
     *
     * @return {@link Boolean}
     *
     * @since 1.1.0
     */
    public static boolean equals(String value, String... values) {
        if (isNotEmpty(value) && isNotEmpty(values)) {
            for (String s : values) {
                if (s.equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断字符串的长度是否在某个范围
     *
     * @param string 字符串
     * @param min 最小长度
     * @param max 最大长度
     *
     * @return {@link Boolean}
     *
     * @since 1.1.0
     */
    public static boolean isLimited(String string, int min, int max) {
        return isNotEmpty(string) && string.length() >= min && string.length() <= max;
    }

    /**
     * 检测数组是否不为空
     *
     * @param ts 数组
     *
     * @param <T> 类型
     *
     * @return {@link Boolean}
     *
     * @since 1.1.0
     */
    public static <T> boolean isNotEmpty(T[] ts) {
        return isNotNull(ts) && ts.length > 0;
    }

    /**
     * 检查数组是否为空
     *
     * @param array 数组
     *
     * @return {@link Boolean}
     *
     * @since 1.1.0
     */
    public static boolean isEmpty(int[] array) {
        return isNull(array) || array.length == 0;
    }

    /**
     * 检查数组是否不为空
     *
     * @param array 数组
     *
     * @return {@link Boolean}
     *
     * @since 1.0.9
     */
    public static boolean isNotEmpty(int[] array) {
        return !isEmpty(array);
    }

    /**
     * 检查数组是否为空
     *
     * @param array 数组
     *
     * @return {@link Boolean}
     *
     * @since 1.1.0
     */
    public static boolean isEmpty(byte[] array) {
        return isNull(array) || array.length == 0;
    }

    /**
     * 检查数组是否不为空
     *
     * @param array 数组
     *
     * @return {@link Boolean}
     *
     * @since 1.1.0
     */
    public static boolean isNotEmpty(byte[] array) {
        return !isEmpty(array);
    }

    /**
     * 检查数组是否为空
     *
     * @param array 数组
     *
     * @return {@link Boolean}
     *
     * @since 1.1.0
     */
    public static boolean isEmpty(short[] array) {
        return isNull(array) || array.length == 0;
    }

    /**
     * 检查数组是否不为空
     *
     * @param array 数组
     *
     * @return {@link Boolean}
     *
     * @since 1.1.0
     */
    public static boolean isNotEmpty(short[] array) {
        return !isEmpty(array);
    }

    /**
     * 检查数组是否为空
     *
     * @param array 数组
     *
     * @return {@link Boolean}
     *
     * @since 1.1.0
     */
    public static boolean isEmpty(long[] array) {
        return isNull(array) || array.length == 0;
    }

    /**
     * 检查数组是否不为空
     *
     * @param array 数组
     *
     * @return {@link Boolean}
     *
     * @since 1.1.0
     */
    public static boolean isNotEmpty(long[] array) {
        return !isEmpty(array);
    }

    /**
     * 检查数组是否为空
     *
     * @param array 数组
     *
     * @return {@link Boolean}
     *
     * @since 1.1.0
     */
    public static boolean isEmpty(float[] array) {
        return isNull(array) || array.length == 0;
    }

    /**
     * 检查数组是否不为空
     *
     * @param array 数组
     *
     * @return {@link Boolean}
     *
     * @since 1.1.0
     */
    public static boolean isNotEmpty(float[] array) {
        return !isEmpty(array);
    }

    /**
     * 检查数组是否为空
     *
     * @param array 数组
     *
     * @return {@link Boolean}
     *
     * @since 1.1.0
     */
    public static boolean isEmpty(double[] array) {
        return isNull(array) || array.length == 0;
    }

    /**
     * 检查数组是否不为空
     *
     * @param array 数组
     *
     * @return {@link Boolean}
     *
     * @since 1.1.0
     */
    public static boolean isNotEmpty(double[] array) {
        return !isEmpty(array);
    }

    /**
     * 检查数组是否为空
     *
     * @param array 数组
     *
     * @return {@link Boolean}
     *
     * @since 1.1.0
     */
    public static boolean isEmpty(boolean[] array) {
        return isNull(array) || array.length == 0;
    }

    /**
     * 检查数组是否不为空
     *
     * @param array 数组
     *
     * @return {@link Boolean}
     *
     * @since 1.1.0
     */
    public static boolean isNotEmpty(boolean[] array) {
        return !isEmpty(array);
    }

    /**
     * 检查数组是否为空
     *
     * @param array 数组
     *
     * @return {@link Boolean}
     *
     * @since 1.1.0
     */
    public static boolean isEmpty(char[] array) {
        return isNull(array) || array.length == 0;
    }

    /**
     * 检查数组是否不为空
     *
     * @param array 数组
     *
     * @return {@link Boolean}
     *
     * @since 1.1.0
     */
    public static boolean isNotEmpty(char[] array) {
        return !isEmpty(array);
    }

    /**
     * 数组是否不是Null，但是为空
     *
     * @param ts 数组
     * @param <T> 类型
     *
     * @return {@link Boolean}
     *
     * @since 1.1.0
     */
    public static <T> boolean isNotNullButEmpty(T[] ts) {
        return isNotNull(ts) && ts.length < 1;
    }

    /**
     * 数组是否为Null或空
     *
     * @param ts 数组
     * @param <T> 类型
     *
     * @return {@link Boolean}
     *
     * @since 1.1.0
     */
    public static <T> boolean isEmpty(T[] ts) {
        return isNull(ts) || isNotNullButEmpty(ts);
    }

    /**
     * 检测字符串是否为NULL, 字符串为Null返回elseValue
     *
     * @param value 需要检测的字符串
     *
     * @param elseValue string为null返回的字符串
     *
     * @return {@link String}
     *
     * @since 1.1.0
     */
    public static String checkNull(String value, String elseValue) {
        return isNull(value) ? elseValue : value;
    }

    /**
     * 检测字符串是否为NULL
     *
     * @param value 需要检测的字符串
     *
     * @return {@link String}
     *
     * @since 1.1.0
     */
    public static String checkNull(String value) {
        return checkNull(value, "");
    }

    /**
     * 检查字符串是否以某个后缀结尾
     *
     * @param value 字符串
     *
     * @param suffixes 后缀
     *
     * @return {@link Boolean}
     *
     * @since 1.1.0
     */
    public static boolean endsWith(String value, String... suffixes) {
        if (isNotEmpty(value) && isNotEmpty(suffixes)) {
            for (String suffix : suffixes) {
                if (value.endsWith(suffix)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 检查字符串是否以某个前缀开头
     *
     * @param value 字符串
     * @param prefixes 前缀
     *
     * @return {@link Boolean}
     *
     * @since 1.1.0
     */
    public static boolean startsWith(String value, String... prefixes) {
        if (isNotEmpty(value) && isNotEmpty(prefixes)) {
            for (String prefix : prefixes) {
                if (value.startsWith(prefix)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 是否包含空格
     *
     * @param string 字符串
     *
     * @return {@link Boolean}
     *
     * @since 1.1.0
     */
    public static boolean hasSpace(String string) {
        return string.contains(" ");
    }

    /**
     * 是否包含为空的字符串
     *
     * @param strings 字符串数组
     *
     * @return {@link Boolean}
     *
     * @since 1.1.0
     */
    public static boolean hasEmpty(String... strings) {
        if (isNotNull(strings)) {
            for (String string : strings) {
                if (isEmpty(string)) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    /**
     * 是否有不为空的字符串
     *
     * @param strings 字符串数组
     *
     * @return {@link Boolean}
     *
     * @since 1.1.0
     */
    public static boolean hasNotEmpty(String... strings) {
        return !allEmpty(strings);
    }

    /**
     * 是否全部为空
     *
     * @param strings 字符串数组
     *
     * @return {@link Boolean}
     *
     * @since 1.1.0
     */
    public static boolean allEmpty(String... strings) {
        if (isNotNull(strings)) {
            for (String string : strings) {
                if (isNotEmpty(string)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 是否全不为空
     *
     * @param strings 字符串数组
     *
     * @return {@link Boolean}
     *
     * @since 1.1.0
     */
    public static boolean notAllEmpty(String... strings) {
        return !hasNotEmpty(strings);
    }

    /**
     * 日期是否过期
     *
     * @param date {@link Date}
     *
     * @return {@link Boolean}
     *
     * @since 1.1.0
     */
    public static boolean isExpired(Date date) {
        return date.before(new Date());
    }

    /**
     * 时间戳是否过期
     *
     * @param timestamp 时间戳
     *
     * @return {@link Boolean}
     *
     * @since 1.1.0
     */
    public static boolean isExpired(long timestamp) {
        return timestamp < System.currentTimeMillis();
    }

    /**
     * 日期是否已经开始
     *
     * @param date {@link Date}
     *
     * @return {@link Boolean}
     *
     * @since 1.1.0
     */
    public static boolean isStarted(Date date) {
        return date.after(new Date());
    }

    /**
     * 时间戳是否已经开始
     *
     * @param timestamp 时间戳
     *
     * @return {@link Boolean}
     *
     * @since 1.1.0
     */
    public static boolean isStarted(long timestamp) {
        return timestamp > System.currentTimeMillis();
    }

    /**
     * 检测字符是否是数字
     *
     * @param c 字符
     *
     * @return {@link Boolean}
     *
     * @since 1.1.0
     */
    public static boolean isNumber(char c) {
        return c >= '0' && c <= '9';
    }

    /**
     * 检测字符是否是小写字母
     *
     * @param c 字符
     *
     * @return {@link Boolean}
     *
     * @since 1.1.0
     */
    public static boolean isLowerCase(char c) {
        return c >= 'a' && c <= 'z';
    }

    /**
     * 检测字符是否是大写字母
     *
     * @param c 字符
     *
     * @return {@link Boolean}
     *
     * @since 1.1.0
     */
    public static boolean isUpperCase(char c) {
        return c >= 'A' && c <= 'Z';
    }

    /**
     * 字符是否是字母
     *
     * @param c 字符
     *
     * @return {@link Boolean}
     *
     * @since 1.1.0
     */
    public static boolean isLetter(char c) {
        return isLowerCase(c) || isUpperCase(c);
    }

    /**
     * 检测字符串是否仅包括字母和数字（字母和数字单独出现也可）
     *
     * @param string 字符串
     *
     * @return {@link Boolean}
     *
     * @since 1.1.0
     */
    public static boolean isLetterAndNumber(String string) {
        boolean result = isNotEmpty(string);
        int idx = 0;
        int len = string.length();
        while (result) {
            if (idx >= len) {
                break;
            }
            char c = string.charAt(idx);
            result = isLetter(c) || isNumber(c);
            idx++;
        }
        return result;
    }

    /**
     * 字符串所有字符是否全部是字母
     *
     * @param string 字符串
     *
     * @return {@link Boolean}
     *
     * @since 1.1.0
     */
    public static boolean isLetter(String string) {
        boolean result = isNotEmpty(string);
        int idx = 0;
        int len = string.length();
        while (result) {
            if (idx >= len) {
                break;
            }
            result = isLetter(string.charAt(idx));
            idx++;
        }
        return result;
    }

    /**
     * 检测字符串所有字母是否全部是小写
     *
     * @param string 字符串
     *
     * @return {@link Boolean}
     *
     * @since 1.1.0
     */
    public static boolean isLowerCase(String string) {
        return isNotEmpty(string) && string.equals(string.toLowerCase());
    }

    /**
     * 检测字符串是否全部是小写字母
     *
     * @param string 字符串
     *
     * @return {@link Boolean}
     *
     * @since 1.0.9
     */
    public static boolean isOnlyLowerCase(String string) {
        boolean result = isNotEmpty(string);
        int idx = 0;
        int len = string.length();
        while (result) {
            if (idx >= len) {
                break;
            }
            result = isLowerCase(string.charAt(idx));
            idx++;
        }
        return result;
    }

    /**
     * 检测字符串所有字母是否全部是大写
     *
     * @param string 字符串
     *
     * @return {@link Boolean}
     *
     * @since 1.1.0
     */
    public static boolean isUpperCase(String string) {
        return isNotEmpty(string) && string.equals(string.toUpperCase());
    }

    /**
     * 检测字符串是否全部是大写字母
     *
     * @param string 字符串
     *
     * @return {@link Boolean}
     *
     * @since 1.1.0
     */
    public static boolean isOnlyUpperCase(String string) {
        boolean result = isNotEmpty(string);
        int idx = 0;
        int len = string.length();
        while (result) {
            if (idx >= len) {
                break;
            }
            result = isUpperCase(string.charAt(idx));
            idx++;
        }
        return result;
    }

    /**
     * 从集合中获取第一个不为Null的值（当集合中所有值都为null，方法仍然返回null）
     *
     * @param ts 集合
     *
     * @param <T> 值类型
     *
     * @return 值
     *
     * @since 1.1.0
     */
    @SafeVarargs
    public static <T> T getFirstNotNull(T... ts) {
        if (isNotEmpty(ts)) {
            for (T t : ts) {
                if (isNotNull(t)) {
                    return t;
                }
            }
        }
        return null;
    }

    /**
     * 从集合中获取第一个不为Null的值
     *
     * @param ts 集合
     * @param <T> 值类型
     *
     * @return 值
     *
     * @throws Exception 当集合中所有值都为null，抛出异常
     * @since 1.1.0
     */
    @SafeVarargs
    public static <T> T getNotNullWithException(T... ts) throws Exception {
        if (isNotNull(ts)) {
            for (T t : ts) {
                if (isNotNull(t)) {
                    return t;
                }
            }
        }
        throw new Exception("ops, no value found while value is not null.");
    }

    /**
     * 判断LIST是否不为空
     *
     * @param list 需要判断的LIST
     *
     * @return {@link Boolean}
     */
    public static boolean isNotEmpty(List<?> list) {
        return !isEmpty(list);
    }

    /**
     * 判断LIST是否为空或NULL
     *
     * @param list 需要判断的LIST
     *
     * @return {@link Boolean}
     */
    public static boolean isEmpty(List<?> list) {
        return isNull(list) || list.isEmpty();
    }

    /**
     * 判断MAP是否为不空
     *
     * @param map 需要判断的MAP
     *
     * @return {@link Boolean}
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    /**
     * 判断MAP是否为空或NULL
     *
     * @param map 需要判断的MAP
     *
     * @return {@link Boolean}
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return isNull(map) || map.isEmpty();
    }

    /**
     * 检查对象是否在集合中
     *
     * @param <T> 类型
     * @param t 对象
     * @param ts 集合
     *
     * @return 是否存在
     *
     * @since 1.1.0
     */
    @SafeVarargs
    public static <T> boolean isIn(T t, T... ts) {
        if (isNotNull(t) && isNotNull(ts)) {
            for (Object object : ts) {
                if (t.equals(object)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 检查对象是否在集合中
     *
     * @param <T> 类型
     * @param t 对象
     * @param ts 集合
     *
     * @return 是否存在
     *
     * @since 1.1.0
     */
    public static <T> boolean isIn(T t, List<T> ts) {
        return isIn(t, ts.toArray());
    }

    /**
     * 检查对象是否不存在某个集合
     *
     * @param t 对象
     * @param ts 集合
     * @param <T> 类型
     *
     * @return {@link Boolean}
     *
     * @since 1.1.0
     */
    @SafeVarargs
    public static <T> boolean isNotIn(T t, T... ts) {
        return !isIn(t, ts);
    }

    /**
     * 检查对象是否不存在某个集合
     *
     * @param t 对象
     * @param ts 集合
     * @param <T> 类型
     *
     * @return {@link Boolean}
     *
     * @since 1.1.0
     */
    public static <T> boolean isNotIn(T t, List<T> ts) {
        return isNotIn(t, ts.toArray());
    }

    /**
     * 检查数组是否已经排好序
     *
     * @param nums 数组
     *
     * @return {@link Boolean}
     *
     * @since 1.1.0
     */
    public static boolean isSortedArray(int[] nums) {
        boolean desc = nums[0] - nums[nums.length - 1] >= 0;
        for (int i = 0; i < nums.length - 1; i++) {
            if (!desc && nums[i] > nums[i + 1]) {
                return false;
            }
            if (desc && nums[i] < nums[i + 1]) {
                return false;
            }
        }
        return true;
    }

    /**
     * 文件是否不存在
     *
     * @param file 文件
     *
     * @return 是否不存在
     */
    public static boolean isNotExists(String file) {
        return !isExists(file);
    }

    /**
     * 文件是否存在
     *
     * @param file 文件
     *
     * @return 是否存在
     */
    public static boolean isExists(String file) {
        return new File(Checker.checkNull(file)).exists();
    }

    /**
     * 判断文件是否是图片
     *
     * @param file 文件
     *
     * @return {@link Boolean}
     */
    public static boolean isImage(File file) {
        return isNotNull(file) && isImage(file.getName());
    }

    /**
     * 判断文件是否是图片
     *
     * @param file 文件
     *
     * @return {@link Boolean}
     */
    public static boolean isImage(String file) {
        return isNotEmpty(file) && IMAGES_PATTERN.matcher(file).matches();
    }

    /**
     * 是否为日期格式
     *
     * @param date 需要判断的日期
     *
     * @return {@link Boolean}
     */
    public static boolean isDate(String date) {
        return isNotNull(date) && DATE_PATTERN.matcher(date).matches();
    }

    /**
     * 是否为邮箱格式
     *
     * @param email 需要判断的邮箱地址
     *
     * @return {@link Boolean}
     */
    public static boolean isEmail(String email) {
        return isNotEmpty(email) && EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * 是否为数字（含小数）格式
     *
     * @param decimal 需要判断的数字
     *
     * @return {@link Boolean}
     */
    public static boolean isDecimal(String decimal) {
        return isNotEmpty(decimal) && DECIMAL_PATTERN.matcher(decimal).matches();
    }

    /**
     * 是否为整数格式
     *
     * @param number 需要判断的整数
     *
     * @return {@link Boolean}
     */
    public static boolean isNumber(String number) {
        return isNotEmpty(number) && NUMBER_PATTERN.matcher(number).matches();
    }

    /**
     * 是否为超链接
     *
     * @param hyperLink 需要匹配超链接
     *
     * @return {@link Boolean}
     */
    public static boolean isHyperLink(String hyperLink) {
        return isNotEmpty(hyperLink) && HYPER_LINK_PATTERN.matcher(hyperLink).matches();
    }

    /**
     * 检测日期是否为NULL
     *
     * @param value 需要检测的日期
     *
     * @return {@link Date}
     *
     * @since 1.1.0
     */
    public static Date checkNull(Date value) {
        return checkNull(value, new Date());
    }

    /**
     * 检测日期是否为NULL
     *
     * @param value 需要检测的日期
     *
     * @param elseValue 为null染返回的值
     *
     * @return {@link Date}
     *
     * @since 1.1.0
     */
    public static Date checkNull(Date value, Date elseValue) {
        return isNull(value) ? elseValue : value;
    }

    /**
     * 判断当前系统是否是Windows
     *
     * @return {@link Boolean}
     */
    public static boolean isWindows() {
        return BasicUtils.getCurrentOS().startsWith("windows");
    }

    /**
     * 判断当前系统是否是Mac
     *
     * @return {@link Boolean}
     */
    public static boolean isMacOS() {
        return BasicUtils.getCurrentOS().startsWith("mac");
    }

    /**
     * 判断当前系统是否是Linux
     *
     * @return {@link Boolean}
     */
    public static boolean isLinux() {
        return BasicUtils.getCurrentOS().startsWith("linux");
    }

    /**
     * 自定义检查
     *
     * @param value res为true返回的值
     * @param elseValue res为false返回的值
     * @param res {@link Boolean}
     * @param <T> 值类型
     *
     * @return 结果
     *
     * @since 1.1.0
     */
    public static <T> T check(T value, T elseValue, boolean res) {
        return res ? value : elseValue;
    }

    /**
     * 自定义检查
     *
     * @param value 调用结果为true返回的值
     * @param elseValue 调用结果为false返回的值
     * @param checker 自定义检查方法
     * @param <T> 值类型
     *
     * @return 结果
     *
     * @since 1.1.0
     */
    public static <T> T check(T value, T elseValue, IChecker checker) {
        return check(value, elseValue, checker.check());
    }

}
