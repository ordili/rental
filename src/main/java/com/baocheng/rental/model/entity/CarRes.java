package com.baocheng.rental.model.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.Map;

@Data
public class CarRes {

    private CarResState state;

    private Map<Car,Long> carQuantityMap;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

}
