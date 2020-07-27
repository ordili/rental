package com.chenbao.rental.model.order;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RentalOrderRes extends RentalOrderReq {

    private BigDecimal totalAmount;
    private BigDecimal realAmount;
    private BigDecimal discountAmount;
    private RentalOrderState state;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

}
