package org.tx.netty;

import lombok.Data;

/**
 * @author WangChao
 * @create 2020/12/12 19:56
 */
@Data
public class TxInfo {
    String channelAddress;
    String taskId;
    String methodStr ;
    String address ;
}
