package org.tx.factory.bean;

import java.util.UUID;

/**
 * @author WangChao
 * @create 2020/12/10 5:30
 */
public class Utils {
    public static String buildUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
