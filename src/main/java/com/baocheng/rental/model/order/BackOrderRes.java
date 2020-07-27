package com.baocheng.rental.model.order;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class BackOrderRes extends BackOrderReq {

    private BackOrderState state;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

}
