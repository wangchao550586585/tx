package com.tx;


import com.tx.service.TTestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class TXManagerTestApp {
    @Autowired
    TTestService tTestService;

    @Test
    public void textTx() {
        tTestService.tx();
    }
}


