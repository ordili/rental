package com.chenbao.rental.model.order;

public enum OrderDetailState {
    NORMAL,
    CAR_IS_NULL,
    ORDER_QUANTITY_ILLEGAL,
    DB_QUANTITY_ILLEGAL,
    DAILY_RENT_ILLEGAL,
    TENANCY_ILLEGAL,
    ORDER_DETAIL_IS_NULL,
}
