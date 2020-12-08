package org.tx;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.tx.po.TTest;
import org.tx.service.TTestService;

/**
 * Unit test for simple App.
 */
@SpringBootTest
public class AppTest 
{
        @Autowired
        TTestService tTestService;


    @Test
    public void textInsert() {
        TTest tTest = new TTest();
        tTest.setName("hehelo");
        tTestService.save(tTest);
    }
    }
