package top.jasonkayzk.jutil;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

/**
 * @author zk
 */
public class NetUtils {

    /**
     * 请求头
     */
    public static final String[] USER_AGENT = {
        "mozilla/5.0 (windows nt 10.0; win64; x64) applewebkit/537.36 (khtml, like gecko) chrome/59.0.3071.115 safari/537.36",
        "Mozilla/5.0 (Windows NT  6.1; Win64; x64; rv:47.0) Gecko/20100101  Firefox/47.0",
        "Mozilla/5.0 (Macintosh;Intel  Mac OS X x.y; rv:42.0) Gecko/20100101  Firefox/42.0",
        "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML,  like  Gecko) Chrome/51.0.2704.103 Safari/537.36",
        "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36  (KHTML, like Gecko)  Chrome/51.0.2704.106 Safari/537.36 OPR/38.0.2220.41",
        "Mozilla/5.0 (iPhone;  CPU iPhone OS 10_3_1 like  Mac OS X) AppleWebKit/603.1.30 (KHTML, like Gecko) Version/10.0 Mobile/14E304 Safari/602.1",
        "Mozilla/5.0 (compatible; MSIE 9.0; Windows Phone OS 7.5;  Trident/5.0; IEMobile/9.0)",
        "Mozilla/5.0 (iPhone; U; CPU like Mac  OS X; en) AppleWebKit/420+ (KHTML,  like  Gecko) Version/3.0 Mobile/1A543a Safari/419.3"
    };

    /**
     * 网页响应200
     */
    public static final int RESPONSE_OK = 200;

    /**
     * 获取HttpURLConnection的InputStream对象
     *
     * @param connection 链接对象
     *
     * @return {@link InputStream}
     *
     * @throws IOException 异常
     */
    public static InputStream getInputStreamOfConnection(HttpURLConnection connection) throws IOException {
        return setDefaultValue(connection).getInputStream();
    }

    private static HttpURLConnection setDefaultValue(HttpURLConnection connection) {
        connection.setConnectTimeout(1000 * 6);
        connection.setRequestProperty("Charset", "UTF-8");
        connection.setRequestProperty("User-Agent", NetUtils.USER_AGENT[0]);
        connection.setRequestProperty("Connection", "Keep-Alive");
        connection.setRequestProperty("Accept", "*/*");
        return connection;
    }

}
