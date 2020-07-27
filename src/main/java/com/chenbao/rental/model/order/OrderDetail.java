package com.chenbao.rental.model.order;

import com.alibaba.fastjson.JSONObject;
import com.chenbao.rental.model.entity.Car;
import lombok.Data;

@Data
public class OrderDetail {
    private Long id;
    private Car car;
    private Long orderQuantity;
    private OrderDetailState state;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
