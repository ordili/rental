package com.chenbao.rental.model.order;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.List;

@Data
public class BackOrderReq {

    private Long backOrderId;
    private List<BackOrderDetail> backOrderDetailList;
    private Borrower borrower;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
