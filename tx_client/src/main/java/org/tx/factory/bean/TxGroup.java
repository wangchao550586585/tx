package org.tx.factory.bean;

import com.alibaba.fastjson.JSONObject;
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
    private Long nowTime;

    public static TxGroup parse(String json) {
        TxGroup txGroup = new TxGroup();
        JSONObject parse = JSONObject.parseObject(json);
        txGroup.setGroupId(parse.getString("g"));
        txGroup.setStartTime(parse.getLong("st"));
        txGroup.setNowTime(parse.getLong("nt"));
        return txGroup;
    }
}
