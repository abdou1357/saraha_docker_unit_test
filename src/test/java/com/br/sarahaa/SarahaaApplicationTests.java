package com.br.sarahaa;

import com.br.sarahaa.services.MessageServiceTest;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Suite.SuiteClasses({
        MessageServiceTest.class
})
class SarahaaApplicationTests {

    @Test
    void contextLoads() {
    }

}
