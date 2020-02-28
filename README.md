![https://img.shields.io/badge/license-MIT-blue.svg?longCache=true&style=flat-square](https://img.shields.io/badge/license-MIT-blue.svg?longCache=true&style=flat-square) [![Build Status](https://www.travis-ci.org/JasonkayZK/jutil.svg?branch=master)](https://www.travis-ci.org/JasonkayZK/jutil)

一个封装了Java Web开发常用功能的库(**JDK要求至少1.8及以上**)

本仓库将持续更新!

**使用Maven**

``` xml
<dependency>
    <groupId>top.jasonkayzk</groupId>
    <artifactId>jutil</artifactId>
    <version>1.0.0-RELEASE</version>
</dependency>
```

> [**GitHub地址**](https://github.com/jasonkayzk/jutil/)

**0.`top.jasonkayzk.jutil.BasicUtils`类**

|方法名|简要说明|
|------|---------------|
|getCurrentOS|获取当前操作系统名称|


**1.`top.jasonkayzk.jutil.RandomUtils`类**

|方法名|简要说明|
|------|---------------|
|getRandomInteger|按照length, 或者range获取一个随机的整型|
|getRandomIntegerIgnoreRange|获取一个可以忽略某个区间的随机整型|
|getRandomString|获取一个随机字符串|
|getRandomText|安装给定ascii范围, 获取一个随机字符串|
|getRandomTextIgnoreRange|获取一个可以忽略某个范围字符的随机字符串|
|getRandomStringWithoutSymbol|获取一个只有数字和字母的随机字符串|
|getRandomStringOnlyLetter|获取一个只有字母的随机字符串|
|getRandomStringOnlyLowerCase|获取一个只有小写字母的随机字符串|
|getRandomStringOnlyUpperCase|获取一个只有大写字母的随机字符串|
|getRandomDouble|获取一个随机的双精度类型|
|getRandomNumber|获取一个指定长度的随机数字字符串|
|getRandomColor|获取一个指定透明度的随机颜色(R,G,B)|

**2. `top.jasonkayzk.util.Checker`类**

*所有参数均不用考虑为null的情况，Checker会处理*

| 方法名           | 简要说明                             |
| ------------- | -------------------------------- |
| check        | 自定义检查, 传入函数式接口                  |
| isNull        | 对象是否为null                        |
| isNotNull     | 对象是否不为null                       |
| checkNull     | 检测字符串是否为null，如果是，返回空字符串，否则返回原字符串 |
| isEmpty       | 列表或集合是否为空                        |
| isNullOrEmpty | 字符串是否为null或空                     |
| isNotEmpty    | 是否不为null且不为空                     |
| replace       | 替换字符串或字符                            |
| equals        | 检查字符串是否等于某个字符串                |
| startsWith        | 检查字符串是否以某个前缀开头                |
| endsWith        | 检查字符串是否以某个后缀结尾                |
| hasSpace        | 是否包含空格                |
| hasEmpty        | 是否包含为空的字符串                |
| isExpired        | 日期/时间戳是否过期                |
| isStarted        | 日期/时间戳是否已经开始               |
| isLowerCase        | 检测字符/字符串是否是小写字母               |
| isUpperCase        | 检测字符/字符串是否是大写字母               |
| isLetter        | 字符/字符串是否是字母               |
| isLetterAndNumber        | 检测字符串是否仅包括字母和数字（字母和数字单独出现也可）               |
| isLimited      | 判断字符串的长度是否在某个范围                     |
| getFirstNotNull      | 从集合中获取第一个不为Null的值（当集合中所有值都为null，方法仍然返回null）                     |
| getNotNullWithException      | 从集合中获取第一个不为Null的值                     |
| isIn      | 检查对象是否在集合中                     |
| isNotIn      | 检查对象是否不存在某个集合                     |
| isExists      | 文件是否存在                     |
| isNotExists      | 文件是否不存在                     |
| isDate        | 是否是日期格式                          |
| isEmail       | 是否是邮箱格式                          |
| isDecimal     | 是否是数字（包括小数）格式                    |
| isNumber      | 是否是整数                            |
| isHyperLink   | 是否是超链接（URL）                      |
| isSorted      | 检查数组是否是升序或降序                     |
| checkDate     | 检测日期是否为null，如果是，返回当前日期，否则返回原日期   |
| isWindows |判断当前系统是否是Windows|
| isMacOS |判断当前系统是否是Mac|
| isLinux |判断当前系统是否是Linux|
| isLimited |判断字符串的长度是否在某个范围|
| isImage |判断文件是否是图片|


**3. `top.jasonkayzk.util.ReflectUtils`类**

|方法名|简要说明|
|------|---------------|
|getMethodMap|获取类的所有方法，并封装成集合|
|getTypes|获取所有对象类型|
|getBasicTypes|获取所有对象的基本类型|
|getMethodMap|获取类的方法，并封装成集合|
|executeExpression|将字符串转成代码，并执行|
|invokeMethod|通过反射调用方法|
|invokeMethodUseBasicType|使用基本类型调用方法|
|addClassesInPackageByFile|以文件的形式来获取包下的所有类|
|getClasses|从包中获取所有的类|
|scanPackage|扫描包下面的所有类|


**4. `top.jasonkayzk.util.LoggerUtils`类**

|方法名|简要说明|
|------|---------------|
|getLogger| 通过Class/Object/LoggerUtils自身/甚至null获取`org.apache.log4j.Logger` |
|log|打印日志,可指定参数,日志级别等,默认日志等级为INFO|
|info|打印信息日志|
|warn|打印警告日志|
|error|打印错误日志|
|debug|打印调试日志|
|fatal|打印严重错误日志|


**5. 发送邮件**

``` java
// 导包
import top.jasonkayzk.jutil.MailUtils;
import top.jasonkayzk.jutil.enums.MailHostEnum;

// 使用: 下面的key指的是开启了POP3/IMAP/SMTP/Exchange/CardDAV/CalDAV服务，生成的授权码
// MailHostEnum.QQ 即为 "smtp.qq.com", 支持: QQ, 163, GMAIL, OUTLOOK, SINA

MailUtils.config(MailHostEnum.QQ, "Jasonkay", "271226192@qq.com", "你的授权key", 465);
MailUtils.sendMail("xxxxxx@163.com", "测试邮件", "这是一封测试邮件");
```


**6. 网络文件下载**

**注:** 内部已经使用了Checker判断了url的合法性!

*使用*

``` java
// 1. 导包
import top.jasonkayzk.jutil.DownloadUtils;

// 2. 下载
String storagePath = "/path", url = "http://fileUrl";
Downloader.download(storagePath, url);
```

