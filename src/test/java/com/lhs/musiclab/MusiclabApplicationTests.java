package com.lhs.musiclab;

import com.lhs.musiclab.pojo.MLabUser;
import com.lhs.musiclab.service.MLabUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MusiclabApplicationTests {
    @Autowired
    MLabUserService mLabUserService;
    @Autowired
    DataSource dataSource;
    Logger logger = LoggerFactory.getLogger(getClass());
    @Test
    public void contextLoads() {
        List<MLabUser> list = mLabUserService.list();
        logger.info("测试--"+list.get(0));
        logger.debug("测试--");
    }

}
