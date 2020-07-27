package com.chenbao.rental.model.order;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * @author Jidong Li
 * @Data 2020/07/25
 * Information of the borrower
 */
@Data
public class Borrower {

    private Long id;
    private String name;
    private String phoneCode;
    private String IdNumber;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
