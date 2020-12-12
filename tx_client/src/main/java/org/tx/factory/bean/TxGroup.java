package org.tx.factory.bean;

import lombok.Data;

/**
 * @author wangchao
 * @description: TODO
 * @date 2020/12/12 9:34
 */
@Data
public class TxGroup {
    private String groupId;
    private Long startTime;

    public static TxGroup parse(String json) {
        return null;
    }
}
