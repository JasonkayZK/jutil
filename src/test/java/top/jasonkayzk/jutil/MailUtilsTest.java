package top.jasonkayzk.jutil;

import org.junit.Test;
import top.jasonkayzk.jutil.enums.MailHostEnum;

public class MailUtilsTest {

    @Test
    public void sendMail() throws Exception {
        MailUtils.config(MailHostEnum.QQ, "Jasonkay", "xxxxxxxx@qq.com", "你的授权key", 465);
        MailUtils.sendMail("xxxxxx@163.com", "测试邮件", "这是一封测试邮件");
    }

}