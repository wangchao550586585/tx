package org.tx.netty;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangchao
 * @description: TODO
 * @date 2020/12/11 9:16
 */
@Data
public class TxGroup {
    private String groupId;
    private Long startTime;
    private Long nowTime;
    private List<TxInfo> txInfoList = new ArrayList<>();

    public void addTxInfo(TxInfo txInfo) {
        txInfoList.add(txInfo);
    }

    public static TxGroup parse(String json) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        TxGroup txGroup = new TxGroup();
        txGroup.setGroupId(jsonObject.getString("g"));
        txGroup.setStartTime(jsonObject.getLong("st"));
        return txGroup;
    }

    public String toJsonString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("g", groupId);
        jsonObject.put("st", startTime);
        jsonObject.put("nt", nowTime);
        return jsonObject.toString();
    }

    public String toAllJsonString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("g", groupId);
        jsonObject.put("st", startTime);
        jsonObject.put("nt", nowTime);

        JSONArray jsonArray = new JSONArray();
        txInfoList.forEach(k -> {
            JSONObject item = new JSONObject();
            item.put("ca", k.getChannelAddress());
            item.put("k", k.getTaskId());
            item.put("ms", k.getMethodStr());
            item.put("a", k.getAddress());
            jsonArray.add(item);
        });
        jsonObject.put("l", jsonArray);
        return jsonObject.toString();
    }


}
