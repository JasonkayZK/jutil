package top.jasonkayzk.jutil;

import org.junit.Test;

import static org.junit.Assert.*;

public class DownloadUtilsTest {

    @Test
    public void download() {
        DownloadUtils.download("/home/zk/workspace/test/", "http://pic.cssn.cn/tp/tp_tpqh/201911/W020191114592042444708.jpg");
    }
}