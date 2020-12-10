package org.tx.factory.bean;

import lombok.Data;

import java.sql.ResultSet;

/**
 * @author WangChao
 * @create 2020/12/10 5:27
 */
@Data
public class Request {
    private String key;
    private String action;
    private String params;

    public Request(String action, String params) {
        this.action = action;
        this.params = params;
        this.key = Utils.buildUUID();
    }

    public String toMsg() {
        return "{\"a\":\"" + getAction() + "\",\"k\":\"" + getKey() + "\",\"p\":" + getParams() + "}";
    }
}
