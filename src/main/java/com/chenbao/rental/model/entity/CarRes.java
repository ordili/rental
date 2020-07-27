package com.chenbao.rental.model.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Data
public class CarRes {

    private CarResState state;

    private Map<Car,Long> carQuantityMap;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

}
