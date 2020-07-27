package com.chenbao.rental.model.order;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
public class  RentalOrderDetail extends OrderDetail {

    /**
     * The format is yyyy-MM-dd
     */
    private LocalDate fromDay;
    private LocalDate toDay;
    private BigDecimal dailyRent;
    private BigDecimal totalAmount;


    private Long actualRentalQuantity;
    /**
     * the amount after discount.
     */
    private BigDecimal realAmount;
    private BigDecimal discount = BigDecimal.ONE;
    private BigDecimal discountAmount;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
