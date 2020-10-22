package com.bjy.javademo.httpaspect;

import com.bjy.javademo.utils.RedisUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class HttpAspect {

    @Autowired
    private RedisUtil redisUtil;

    @Pointcut(value = "@annotation(com.bjy.javademo.httpaspect.HttpFrequency)")
    public void httpFrequencyAop() {
    }

    @Before("httpFrequencyAop()&&@annotation(httpFrequency)")
    public void before(HttpFrequency httpFrequency) {
        String key = httpFrequency.key();
        int frequency = httpFrequency.frequency();
        int time = httpFrequency.time();
        if (!flowControl(key, frequency,time)) {
            System.out.println("访问次数受限,请稍后再试");
        } else {
            System.out.println("请求成功");
        }
    }

    public boolean flowControl(String key, int frequency,int time) {
        //最大允许100
        int max = frequency;
        long total = 0L;
        if (redisUtil.get(key) == null) {
            //jedisInstance是Jedis连接实例，可以使单链接也可以使用链接池获取，实现方式请参考之前的blog内容
            //如果redis目前没有这个key，创建并赋予0，有效时间为time
            redisUtil.setbyMillisecond(key, 1, time);
        } else {
            //获取加1后的值
            total = redisUtil.incr(key, 1L);
            //Redis TTL命令以秒为单位返回key的剩余过期时间。当key不存在时，返回-2。当key存在但没有设置剩余生存时间时，返回-1。否则，以秒为单位，返回key的剩余生存时间。
            if (redisUtil.getExpire(key) == 0) {
                //为给定key设置生存时间，当key过期时(生存时间为0)，它会被自动删除。
                redisUtil.expireByMillisecond(key, time);
            }
        }
        long keytotaltransations = max;
        //判断是否已超过最大值，超过则返回false
        if (total > keytotaltransations) {
            return false;
        }
        return true;
    }
}
