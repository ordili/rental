package com.chenbao.rental.model.order;

public enum BackOrderState {
    NORMAL,
    BACK_ORDER_IS_NULL,
    BACK_ORDER_DETAIL_IS_NULL,
    BORROWER_IS_NULL,
    ALL_BACK_ORDER_DETAIL_IS_EXCEPTION,
}
