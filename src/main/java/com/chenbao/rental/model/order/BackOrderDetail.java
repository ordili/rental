package com.chenbao.rental.model.order;

import com.alibaba.fastjson.JSONObject;
import com.chenbao.rental.model.entity.Car;
import lombok.Data;


@Data
public class BackOrderDetail extends OrderDetail {

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
