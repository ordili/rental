package com.baocheng.rental.model.order;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.List;

@Data
public class RentalOrderReq {

    private Long rentalOrderReqId;
    private List<RentalOrderDetail> rentalOrderDetailList;
    private Borrower borrower;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
