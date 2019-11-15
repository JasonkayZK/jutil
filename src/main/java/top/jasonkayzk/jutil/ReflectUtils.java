package top.jasonkayzk.jutil;

import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.jexl3.MapContext;
import org.apache.commons.jexl3.internal.Engine;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 反射相关工具类
 *
 * @author zk
 */
public class ReflectUtils {

    private static Logger logger = Logger.getLogger(ReflectUtils.class);

    private static JexlEngine jexlEngine = new Engine();

    private ReflectUtils() {}

    /**
     * 获取类的所有方法，并封装成集合
     *
     * @param clazz 类
     *
     * @return {@link Map}
     *
     * @since 1.1.0
     */
    public static Map<String, Method> getMethodMap(Class<?> clazz) {
        return getMethodMap(clazz, null);
    }

    /**
     * 获取所有对象类型
     *
     * @param objects 对象
     *
     * @return 所有对象类型
     *
     * @since 1.1.0
     */
    public static Class<?>[] getTypes(Object[] objects) {
        if (!Checker.isNull(objects)) {
            Class<?>[] classes = new Class<?>[objects.length];
            int i = 0;
            for (Object object : objects) {
                classes[i++] = Checker.isNull(object) ? null : object.getClass();
            }
            return classes;
        }
        return null;
    }

    /**
     * 获取所有对象的基本类型
     *
     * @param objects 对象
     *
     * @return 所有对象类型
     */
    public static Class<?>[] getBasicTypes(Object[] objects) {
        if (!Checker.isNull(objects)) {
            Class<?>[] classes = new Class<?>[objects.length];
            int i = 0;
            for (Object object : objects) {
                if (Checker.isNull(object)) {
                    classes[i] = null;
                } else if (object instanceof Integer) {
                    classes[i] = int.class;
                } else if (object instanceof Long) {
                    classes[i] = long.class;
                } else if (object instanceof Short) {
                    classes[i] = short.class;
                } else if (object instanceof Byte) {
                    classes[i] = byte.class;
                } else if (object instanceof Float) {
                    classes[i] = float.class;
                } else if (object instanceof Double) {
                    classes[i] = double.class;
                } else if (object instanceof Boolean) {
                    classes[i] = boolean.class;
                } else if (object instanceof Character) {
                    classes[i] = char.class;
                } else {
                    classes[i] = object.getClass();
                }
                i++;
            }
            return classes;
        }
        return null;
    }

    /**
     * 获取类的方法，并封装成集合
     *
     * @param clazz 类
     *
     * @param prefix 过滤前缀
     *
     * @return {@link Map}
     *
     * @since 1.1.0
     */
    public static Map<String, Method> getMethodMap(Class<?> clazz, String prefix) {
        Method[] methods = clazz.getMethods();
        Map<String, Method> methodMap = new HashMap<>(32);
        for (Method method : methods) {
            if (Checker.isEmpty(prefix) || method.getName().startsWith(prefix)) {
                methodMap.put(method.getName(), method);
            }
        }
        return methodMap;
    }

    /**
     * 将字符串转成代码，并执行
     * <p>
     * <br><a href="https://blog.csdn.net/qq_26954773/article/details/80379015#3-%E6%B5%8B%E8%AF%95">怎么使用</a>
     *
     * @param jexlExpression 代码表达式
     *
     * @param map 参数映射
     *
     * @return 执行结果
     *
     * @since 1.1.0
     */
    public static Object executeExpression(String jexlExpression, Map<String, Object> map) {
        JexlExpression expression = jexlEngine.createExpression(jexlExpression);
        JexlContext context = new MapContext();
        if (Checker.isNotEmpty(map)) {
            map.forEach(context::set);
        }
        return expression.evaluate(context);
    }

    /**
     * 调用方法
     *
     * @param object 对象
     *
     * @param methodName 方法名
     *
     * @param parameters 参数
     *
     * @return 方法返回的结果
     *
     * @throws NoSuchMethodException 异常
     *
     * @throws InvocationTargetException 异常
     *
     * @throws IllegalAccessException 异常
     */
    public static Object invokeMethod(Object object, String methodName, Object[] parameters) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return invokeMethod(object, methodName, getTypes(parameters), parameters);
    }

    /**
     * 调用方法
     *
     * @param object 对象
     *
     * @param methodName 方法名
     *
     * @param parameterTypes 参数类型
     *
     * @param parameters 参数
     *
     * @return 方法返回的结果
     *
     * @throws NoSuchMethodException 异常
     * @throws InvocationTargetException 异常
     * @throws IllegalAccessException 异常
     */
    public static Object invokeMethod(Object object, String methodName, Class<?>[] parameterTypes, Object[] parameters) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (Checker.isEmpty(parameters)) {
            return object.getClass().getMethod(methodName).invoke(object);
        } else {
            return object.getClass().getMethod(methodName, parameterTypes).invoke(object, parameters);
        }
    }

    /**
     * 调用方法
     *
     * @param object 对象
     *
     * @param methodName 方法名
     *
     * @param parameters 参数
     *
     * @return 方法返回的结果
     *
     * @throws NoSuchMethodException 异常
     *
     * @throws InvocationTargetException 异常
     *
     * @throws IllegalAccessException 异常
     */
    public static Object invokeMethodUseBasicType(Object object, String methodName, Object[] parameters) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return invokeMethod(object, methodName, getBasicTypes(parameters), parameters);
    }

    /**
     * 以文件的形式来获取包下的所有类
     *
     * @param packageName 包名
     *
     * @param packagePath 包路径
     *
     * @param classes 文件列表
     *
     * @throws ClassNotFoundException 异常
     */
    public static void addClassesInPackageByFile(String packageName, String packagePath, List<Class<?>> classes) throws ClassNotFoundException {
        File dir = new File(packagePath);
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        File[] files = dir.listFiles(file -> (file.isDirectory()) || (file.getName().endsWith(".class")));
        if (Checker.isNotNull(files)) {
            for (File file : files) {
                if (file.isDirectory()) {
                    addClassesInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), classes);
                } else {
                    String className = file.getName().substring(0, file.getName().length() - 6);
                    classes.add(Class.forName(packageName + '.' + className));
                }
            }
        }
    }

    /**
     * 从包中获取所有的类
     *
     * @param packageName 包名
     *
     * @return {@link List}
     *
     * @throws IOException 异常
     *
     * @throws ClassNotFoundException 异常
     */
    public static List<Class<?>> getClasses(String packageName) throws IOException, ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        String packageDirName = packageName.replace('.', '/');
        Enumeration<URL> dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
        while (dirs.hasMoreElements()) {
            URL url = dirs.nextElement();
            String protocol = url.getProtocol();
            if ("file".equals(protocol)) {
                String filePath = URLDecoder.decode(url.getFile(), StandardCharsets.UTF_8);
                addClassesInPackageByFile(packageName, filePath, classes);
            } else if ("jar".equals(protocol)) {
                JarFile jar = ((JarURLConnection) url.openConnection()).getJarFile();
                Enumeration<JarEntry> entries = jar.entries();
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    String name = entry.getName();
                    if (name.charAt(0) == '/') {
                        name = name.substring(1);
                    }
                    if (name.startsWith(packageDirName)) {
                        int idx = name.lastIndexOf('/');
                        if (idx != -1) {
                            packageName = name.substring(0, idx).replace('/', '.');
                        }
                        if (name.endsWith(".class") && !entry.isDirectory()) {
                            String className = name.substring(packageName.length() + 1, name.length() - 6);
                            classes.add(Class.forName(packageName + '.' + className));
                        }
                    }
                }
            }
        }
        return classes;
    }

    /**
     * 扫描包下面的所有类
     *
     * @param packages 包名
     *
     * @return {@link List}
     *
     * @throws IOException 异常
     *
     * @throws ClassNotFoundException 异常
     */
    public static List<Class<?>> scanPackage(String... packages) throws IOException, ClassNotFoundException {
        List<Class<?>> list = new ArrayList<>();
        for (String pkg : packages) {
            list.addAll(getClasses(pkg));
        }
        return list;
    }

}
