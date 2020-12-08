package org.tx;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.tx.service.TTestService;

/**
 * Unit test for simple App.
 */
@SpringBootTest
public class AppTest {
    @Autowired
    TTestService tTestService;

    @Test
    public void textTx() {
        tTestService.saveTTest();
    }


}
