package com.chenbao.rental.utils;

import com.chenbao.rental.controller.RentalCarController;
import com.chenbao.rental.model.entity.Car;
import com.chenbao.rental.model.order.BackOrderDetail;
import com.chenbao.rental.model.order.OrderDetail;
import com.chenbao.rental.model.order.OrderDetailState;
import com.chenbao.rental.model.order.RentalOrderDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.chenbao.rental.model.order.OrderDetailState.*;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDetailUtils {

    private static Logger log = LoggerFactory.getLogger(RentalCarController.class);

    /**
     * Function:
     * 1. Check the fromDay and toDay isn't null, if not, return false.
     * 2. Check the fromDay is after the toDay, if not, return false.
     *
     * @return return true if fromDay and toDay isn't null and the toDay is after the fromDay, else return false.
     */
    public static boolean checkFromDayAndToDayIsLegal(RentalOrderDetail rentalOrderDetail) {

        if (rentalOrderDetail == null) {
            return false;
        }

        LocalDate fromDay = rentalOrderDetail.getFromDay();
        LocalDate toDay = rentalOrderDetail.getToDay();

        if (fromDay == null || rentalOrderDetail.getToDay() == null) {
            String error = "The fromDay or toDay is null";
            log.error(error);
            return false;
        }

        int days = Period.between(rentalOrderDetail.getFromDay(), rentalOrderDetail.getToDay()).getDays();
        if (days < 0) {
            String error = String.format(
                    "fromDay must after toDay, but hte fromDay is %s and the toDay is %s",
                    LocalDateFormatterUtils.fmt.format(fromDay),
                    LocalDateFormatterUtils.fmt.format(toDay)
            );
            log.error(error);
            return false;
        }

        return true;
    }

    /**
     * Check rental order detail state is right, and set the state to normal or exception state.
     *
     * @param rentalOrderDetail
     */
    public static void checkRentalOrderAndSetState(RentalOrderDetail rentalOrderDetail) {

        if (rentalOrderDetail == null) {
            rentalOrderDetail.setState(ORDER_DETAIL_IS_NULL);
            return;
        }
        if (rentalOrderDetail.getCar() == null) {
            rentalOrderDetail.setState(CAR_IS_NULL);
            return;
        }

        Long orderQuantity = rentalOrderDetail.getOrderQuantity();
        if (orderQuantity == null || orderQuantity.compareTo(1L) < 0) {
            rentalOrderDetail.setState(ORDER_QUANTITY_ILLEGAL);
            return;
        }
        BigDecimal dailyRent = rentalOrderDetail.getDailyRent();
        if (dailyRent == null || dailyRent.compareTo(BigDecimal.ZERO) <= 0) {
            rentalOrderDetail.setState(DAILY_RENT_ILLEGAL);
            return;
        }

        if (!checkFromDayAndToDayIsLegal(rentalOrderDetail)) {
            rentalOrderDetail.setState(TENANCY_ILLEGAL);
            return;
        }

        BigDecimal discount = rentalOrderDetail.getDiscount() == null ? BigDecimal.ONE : rentalOrderDetail.getDiscount();

        if (discount.compareTo(BigDecimal.ZERO) <= 0 || discount.compareTo(BigDecimal.ONE) >= 0) {
            rentalOrderDetail.setDiscount(BigDecimal.ONE);
        }

        rentalOrderDetail.setState(NORMAL);
    }


    /**
     * Check back order detail state is right, and set the state to normal or exception state.
     *
     * @param backOrderDetail
     */
    public static void checkBackOrderAndSetState(BackOrderDetail backOrderDetail) {

        if (backOrderDetail == null) {
            backOrderDetail.setState(ORDER_DETAIL_IS_NULL);
            return;
        }
        if (backOrderDetail.getCar() == null) {
            backOrderDetail.setState(CAR_IS_NULL);
            return;
        }

        Long orderQuantity = backOrderDetail.getOrderQuantity();
        if (orderQuantity == null || orderQuantity.compareTo(1L) < 0) {
            backOrderDetail.setState(ORDER_QUANTITY_ILLEGAL);
            return;
        }

        backOrderDetail.setState(NORMAL);
    }


    /**
     * Calculate the totalAmount according to rentalOrderDetailList.
     *
     * @return
     */
    public static BigDecimal calculateTotalAmount(List<RentalOrderDetail> rentalOrderDetailList) {

        if (rentalOrderDetailList == null || rentalOrderDetailList.isEmpty()) {
            return null;
        }

        BigDecimal totalPrice = rentalOrderDetailList
                .parallelStream()
                .filter(rentalOrderDetail -> orderDetailIsValid(rentalOrderDetail))
                .map(RentalOrderDetail::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalPrice;
    }

    /**
     * Calculate the discountAmount according to rentalOrderDetailList.
     *
     * @return
     */
    public static BigDecimal calculateDiscountAmount(List<RentalOrderDetail> rentalOrderDetailList) {

        if (rentalOrderDetailList == null || rentalOrderDetailList.isEmpty()) {
            return null;
        }

        BigDecimal discountAmount = rentalOrderDetailList
                .parallelStream()
                .filter(rentalOrderDetail -> orderDetailIsValid(rentalOrderDetail))
                .map(RentalOrderDetail::getDiscountAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return discountAmount;
    }

    private static boolean orderDetailIsValid(RentalOrderDetail rentalOrderDetail) {
        return rentalOrderDetail.getState() == NORMAL && rentalOrderDetail.getActualRentalQuantity() > 0L;
    }


    /**
     * Calculate the real amount, the real amount equals total amount subtract the discount amount.
     *
     * @return the real amount or null.
     */
    public static BigDecimal calculateRealAmount(List<RentalOrderDetail> rentalOrderDetailList) {
        BigDecimal totalAmount = calculateTotalAmount(rentalOrderDetailList);
        BigDecimal discountAmount = calculateDiscountAmount(rentalOrderDetailList);

        if (discountAmount == null) {
            return totalAmount;
        }

        if (totalAmount != null) {
            return totalAmount.subtract(discountAmount);
        }

        return null;
    }

    /**
     * Calculate the car rental record of each borrower according to OrderDetails
     *
     * @param lists
     * @return
     */
    public static <T extends OrderDetail> Map<Car, Long> getBorrowerRecordFromOrderDetailsTemplate(List<T> lists, String quantityMethod) {

        if (lists == null || lists.isEmpty()) {
            return null;
        }

        Map<Car, Long> carQuantityMap = new HashMap<>();

        for (T orderDetail : lists) {

            if (orderDetail == null
                    || orderDetail.getState() != OrderDetailState.NORMAL
                    || orderDetail.getCar() == null) {
                continue;
            }

            Car car = orderDetail.getCar();
            Long quantity = 0L;
            try {

                Class<?> cls = orderDetail.getClass();
                Method m = cls.getMethod(quantityMethod);
                quantity = (Long) m.invoke(orderDetail);
            } catch (Exception exception) {
                log.error(exception.getMessage());
                continue;
            }

            if (quantity == null) {
                continue;
            }

            if (quantity <= 0) {
                log.error("quantity is less or equal zero about " + orderDetail);
                continue;
            }

            quantity += carQuantityMap.getOrDefault(car, 0L);

            carQuantityMap.put(car, quantity);

        }

        return carQuantityMap;

    }


}
