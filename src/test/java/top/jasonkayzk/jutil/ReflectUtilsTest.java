package top.jasonkayzk.jutil;

import org.junit.Test;
import top.jasonkayzk.jutil.model.Color;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ReflectUtilsTest {

    @Test
    public void getMethodMap() {
        System.out.println(ReflectUtils.getMethodMap(ReflectUtils.class));
    }

    @Test
    public void getTypes() {
        System.out.println(Arrays.toString(ReflectUtils.getTypes(new Object[]{Double.valueOf("2.2"), Integer.valueOf("1")})));
    }

    @Test
    public void getBasicTypes() {
        System.out.println(Arrays.toString(ReflectUtils.getBasicTypes(new Object[]{Double.valueOf("2.2"), Integer.valueOf("1")})));
    }

    @Test
    public void testGetMethodMap() {
        System.out.println(ReflectUtils.getMethodMap(ReflectUtils.class, "get"));
    }

    @Test
    public void executeExpression() {
        Map<String, Object> map = new HashMap<>();
        map.put("alive", "coding every day");
        map.put("out", System.out);
        String expression = "out.print(alive)";
        ReflectUtils.executeExpression(expression, map);
    }

    @Test
    public void invokeMethod() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        ReflectUtils.invokeMethod(System.out, "println", new String[]{"method invoked!"});
    }

    @Test
    public void testInvokeMethod() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        ReflectUtils.invokeMethod(System.out, "println", new Class[]{String.class}, new String[] {"method invoked"});
    }

    @Test
    public void addClassesInPackageByFile() {

    }

    @Test
    public void getClasses() throws IOException, ClassNotFoundException {
        System.out.println(ReflectUtils.getClasses("top.jasonkayzk.jutil"));
    }

    @Test
    public void scanPackage() throws IOException, ClassNotFoundException {
        System.out.println(ReflectUtils.scanPackage("top.jasonkayzk.jutil"));
    }
}
