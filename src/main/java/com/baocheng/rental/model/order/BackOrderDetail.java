package com.baocheng.rental.model.order;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;


@Data
public class BackOrderDetail extends OrderDetail {

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
