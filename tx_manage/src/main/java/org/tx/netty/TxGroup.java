package org.tx.netty;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * @author wangchao
 * @description: TODO
 * @date 2020/12/11 9:16
 */
@Data
public class TxGroup {
    private String groupId;
    private Long startTime;

    public String toJsonString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("g", groupId);
        jsonObject.put("st", startTime);
        return jsonObject.toString();
    }
}
