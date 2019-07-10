package com.lhs.musiclab;

import com.lhs.musiclab.enums.Sex;
import com.lhs.musiclab.pojo.BlogItem;
import com.lhs.musiclab.pojo.MLabUser;
import com.lhs.musiclab.repository.MLabUserRepository;
import com.lhs.musiclab.service.BlogService;
import com.lhs.musiclab.service.MLabUserService;
import com.lhs.musiclab.utils.SendCode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MusiclabApplicationTests {
    @Autowired
    MLabUserService mLabUserService;
    @Autowired
    MLabUserRepository mLabUserRepository;
    @Autowired
    BlogService blogService;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    AmqpAdmin amqpAdmin;
    @Autowired
    DataSource dataSource;
    Logger logger = LoggerFactory.getLogger(getClass());

    /***
     * 通过jedis向redis服务器设置字符
     */
    /*@Test
    public void test1(){
        List<MLabUser> list = mLabUserService.list();
        redisTemplate.opsForValue().set("testUser",list.get(0));
    }*/
    /***
     * 通过jedis获取redis服务器对应键的值
     */
    /*@Test
    public void test2(){
        logger.info(redisTemplate.opsForValue().get("testUser").toString());
    }
*/
    /***
     * rabbitmq发送消息
     */
    @Test
    public void test3(){
        //Message需要自己定义一个消息体内容和消息头
//        rabbitTemplate.send(exchange,routeKey,message);
        List<MLabUser> list = mLabUserService.list();
        rabbitTemplate.convertAndSend("exchange.direct","musiclab.news",list.get(0));
    }

    /***
     * rabbitmq消费消息
     */
    @Test
    public void test4(){
        Object o = rabbitTemplate.receiveAndConvert("musiclab.news");
        logger.debug(String.valueOf(o.getClass()));
        logger.debug("测试rabbitmq:"+o.toString());
    }

    /***
     * rabbitmq定义交换机
     */
    @Test
    public void test5(){
        amqpAdmin.declareExchange(new DirectExchange("amqpadminexchange"));
        logger.debug("创建交换机完成");
    }

    /***
     * rabbitmq绑定交换机和队列
     */
    @Test
    public void test6(){
        amqpAdmin.declareBinding(new Binding("musiclab.news", Binding.DestinationType.QUEUE, "amqpadminexchange", "musiclab.news",null));
    }

    /***
     * es搜索引擎定义索引
     */
    /*@Test
    public void test7(){
        List<MLabUser> list = mLabUserService.list();
        mLabUserRepository.index(list.get(0));
    }*/

    /***
     * es搜索引擎查找对应键
     */
    /*@Test
    public void test8(){
        logger.debug(String.valueOf(mLabUserRepository.findByEmailLike("@qq").isEmpty()));
    }*/

    /***
     * es搜索引擎删除所有
     */
    /*@Test
    public void test9(){
        mLabUserRepository.deleteAll();
    }*/

    /***
     * list测试
     */
    @Test
    public void contextLoads() {
        List<MLabUser> list = mLabUserService.list();
        logger.info("测试--"+list.get(0));
        logger.debug("测试--");
    }

    /**
     * 获取6位随机验证码
     */
    @Test
    public void test11(){
        System.out.println(SendCode.getVerifyCode(SendCode.CODE_NUMBER_CHAR,SendCode.CODE_LENGTH));
    }

    /**
     * 查看字符串长度
     */
    @Test
    public void test12(){
        String s= "http://localhost:8080";
        System.out.println(s.toCharArray().length);
    }

    /**
     * 将表情包重命名
     * @throws IOException
     */
   /* @Test
    public void test13() throws IOException {
        File file = new File("D:\\storage\\MyProject\\网页设计参考素材\\新建文件夹");
        File[] files = file.listFiles();
        //按照最后修改时间升序
        for (int i = 0; i < files.length-1; i++) {
            for (int j = 0; j < files.length-i-1; j++) {
                if (files[j].lastModified()>files[j+1].lastModified()){
                    File file2 = null;
                    file2 = files[j];
                    files[j] = files[j + 1];
                    files[j + 1] = file2;
                }
            }
        }
        int i = 1;
        //改名输出
        for (File f :files) {
            FileInputStream fis = new FileInputStream(f);
            byte[] all = new byte[(int) f.length()];
            fis.read(all);
            fis.close();
            File file1 = new File("D:\\storage\\MyProject\\网页设计参考素材\\qq\\"+i+".png");
            FileOutputStream fos = new FileOutputStream(file1);
            fos.write(all);
            fos.close();
            i++;
        }
    }*/

    /**
     * 快排
     * @param linkedList
     * @param head
     * @param tail
     */
    public static void linkedlistSort(LinkedList<BlogItem> linkedList, int head, int tail){
        int i=head,j=tail,t=i;
        BlogItem pivot = linkedList.get(i);
        if(linkedList.size()>0){
            while (i < j) {
                if (t == j) {
                    i++;
                    while (i < j && pivot.compareTo(linkedList.get(i))>0) {
                        i++;
                    }
                    linkedList.set(j,linkedList.get(i));
                    t = i;
                } else {
                    while (i < j && pivot.compareTo(linkedList.get(j))<0) {
                        j--;
                    }
                    linkedList.set(i, linkedList.get(j));
                    t = j;
                }
            }
            linkedList.set(t, pivot);
            if(t+1<tail){
                linkedlistSort(linkedList,t+1,tail);
            }
            if(t-1>head){
                linkedlistSort(linkedList,head,t-1);
            }

        }
    }

}
