package com.alexpower.sprigbootactivemq;

import com.alexpower.queue.Queue_produce;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootTest(classes = SpringbootActivemqApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
class SpringbootActivemqApplicationTests {

    @Autowired
    private Queue_produce producer;

    @Test
    void contextLoads() {
    }
    @Test
    public void testProduce() throws Exception{
        producer.sendMsg();
    }
}
