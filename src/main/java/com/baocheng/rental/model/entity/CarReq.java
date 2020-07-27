package com.baocheng.rental.model.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class CarReq {

    private Car car;
    private Long quantity;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
