package cn.wangjie.security;

import cn.wangjie.security.entity.po.UserPO;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.security.jwt.JwtHelper;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

/**
 * @program: security
 * @description:
 * @author: WangJie
 * @create: 2018-12-11 16:22
 **/
@Slf4j
public class SampleTest {
    @Test
    public void test(){
        UserPO userPO = new UserPO();
        userPO.setId(1);
        //userPO.setName("[\"a\",\"b\",\"c\"]");
        userPO.setName("name");
        String json  = JSON.toJSONString(userPO);
        log.info(json);
        userPO = (UserPO)JSON.parse(json);
        log.info(""+ userPO);
    }
    @Test
    public void testNode(){
        String str = JwtHelper.decode("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NDQ3NjczOTMsInVzZXJfbmFtZSI6Impzb24iLCJhdXRob3JpdGllcyI6WyJhZG1pbiJdLCJqdGkiOiI3NTZjZWIzYi0xN2E2LTRiMWQtOWY2MC0wM2VmZGEzMjU2MjciLCJjbGllbnRfaWQiOiJhcHBJRF93aiIsInNjb3BlIjpbImFsbCJdfQ.WAP19FlFLilCjpqfGU-HcbrTGhQqAcXOizHARTifTbA").toString();
        System.out.println(str);
    }

    public static long time(Executor executor,int concurrency,final Runnable action) throws InterruptedException {
        final CountDownLatch ready = new CountDownLatch(concurrency);
        final CountDownLatch start = new CountDownLatch(1);
        final CountDownLatch done = new CountDownLatch(concurrency);
        for (int i = 0;i<concurrency;i++){
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    ready.countDown();
                    try {
                        start.await();
                        action.run();
                    }catch (InterruptedException e){
                        Thread.currentThread().interrupt();
                    }finally {
                        done.countDown();
                    }
                }
            });
        }
        ready.await();
        long  startNanos = System.nanoTime();
        start.countDown();
        done.await();
        return System.nanoTime()-startNanos;
    }



}
