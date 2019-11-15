package top.jasonkayzk.jutil;

import org.junit.Test;

public class LoggerUtilsTest {

    @Test
    public void getLogger() {
        LoggerUtils.getLogger().info("test get logger with nothing!");
    }

    @Test
    public void testGetLogger() {
        LoggerUtils.getLogger(this).info("test get logger with object");
        LoggerUtils.getLogger(null).error("test get logger via null");
    }

    @Test
    public void testGetLogger1() {
        LoggerUtils.getLogger(LoggerUtilsTest.class).info("test get logger with class");
        LoggerUtils.getLogger(null).error("test get logger via null");
    }

    @Test
    public void log() {
    }

    @Test
    public void testLog() {
    }

    @Test
    public void testLog1() {
    }

    @Test
    public void testLog2() {
    }

    @Test
    public void info() {
    }

    @Test
    public void testInfo() {
    }

    @Test
    public void testInfo1() {
    }

    @Test
    public void warn() {
    }

    @Test
    public void testWarn() {
    }

    @Test
    public void testWarn1() {
    }

    @Test
    public void error() {
    }

    @Test
    public void testError() {
    }

    @Test
    public void testError1() {
    }

    @Test
    public void debug() {
    }

    @Test
    public void testDebug() {
    }

    @Test
    public void testDebug1() {
    }

    @Test
    public void fatal() {
    }

    @Test
    public void testFatal() {
    }

    @Test
    public void testFatal1() {
    }
}