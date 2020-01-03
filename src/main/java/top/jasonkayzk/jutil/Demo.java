package top.jasonkayzk.jutil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Spliterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

/**
 * @author zk
 */
public class Demo {

    public static void main(String[] args) throws InterruptedException {
        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < 2000; i++) {
            set.add(i);
        }

        Spliterator<Integer> spliterator = set.spliterator();

        // 求和结果
        final AtomicInteger count = new AtomicInteger(0);

        // 开启线程数
        final int threadNum = 4;

        // 计数器锁, 多个线程都处理完毕后才输出结果, 也可以使用join()方法
        final CountDownLatch latch = new CountDownLatch(threadNum);

        // 定义处理线程任务
        Runnable task = () -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("线程" + threadName + "开始运行-----");
            Spliterator<Integer> sp1 = spliterator.trySplit();

            latch.countDown();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sp1.forEachRemaining(x -> System.out.print(x + " "));
            System.out.println();
            System.out.println("线程" + threadName + "运行结束-----");

        };

        for (int i = 0; i < threadNum; i++) {
            new Thread(task).start();
        }

        Thread.sleep(5000);
        System.out.println(count);
    }

    // 判断字符串是数字
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

}
