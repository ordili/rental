package com.chenbao.rental.controller;

import com.chenbao.rental.model.entity.Car;
import com.chenbao.rental.model.order.*;
import com.chenbao.rental.repertory.BorrowerRepertoryImpl;
import com.chenbao.rental.repertory.CarRepertoryImpl;
import com.chenbao.rental.utils.CalculateUtils;
import com.chenbao.rental.utils.OrderDetailUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.chenbao.rental.utils.Commons.GET_ACTUAL_RENTAL_QUANTITY_METHOD_NAME;
import static com.chenbao.rental.utils.Commons.GET_ORDER_QUANTITY_METHOD_NAME;


@RestController
@RequestMapping
@Api(tags = {"RentalCarController"})
public class RentalCarController {

    private Logger log = LoggerFactory.getLogger(RentalCarController.class);

    @Autowired
    private CarRepertoryImpl carRepertoryImpl;

    @Autowired
    private BorrowerRepertoryImpl borrowerRepertory;

    /**
     * Function:
     * Rental cars by rental order.
     *
     * @param rentalOrderReq
     * @return RentalOrderReq, the return rental order contains following information,
     * 1. The number of successful leases by cars.
     * 2. Status of successful lease by cars.
     */
    @PostMapping("rent/cars")
    public RentalOrderRes rentalCar(@RequestBody RentalOrderReq rentalOrderReq) {

        RentalOrderRes rentalOrderRes = new RentalOrderRes();
        if (rentalOrderReq == null) {
            rentalOrderRes.setState(RentalOrderState.ORDER_IS_NULL);
            log.error(RentalOrderState.ORDER_IS_NULL.toString());
            return rentalOrderRes;
        }

        BeanUtils.copyProperties(rentalOrderReq, rentalOrderRes);
        if (rentalOrderReq.getBorrower() == null) {
            rentalOrderRes.setState(RentalOrderState.BORROWER_IS_NULL);
            log.error(RentalOrderState.BORROWER_IS_NULL.toString());
            return rentalOrderRes;
        }

        List<RentalOrderDetail> rentalOrderDetails = rentalOrderRes.getRentalOrderDetailList();

        if (rentalOrderDetails == null || rentalOrderDetails.isEmpty()) {
            rentalOrderRes.setState(RentalOrderState.ORDER_DETAIL_IS_NULL);
            log.error(RentalOrderState.ORDER_DETAIL_IS_NULL.toString());
            return rentalOrderRes;
        }


        for (RentalOrderDetail rentalOrderDetail : rentalOrderDetails) {

            OrderDetailUtils.checkRentalOrderAndSetState(rentalOrderDetail);

            if (rentalOrderDetail.getState() != OrderDetailState.NORMAL) {
                log.info("the order detail state is invalid, the order detail is " + rentalOrderDetail);
                continue;
            }

            Car car = rentalOrderDetail.getCar();
            Long orderQuantity = rentalOrderDetail.getOrderQuantity();
            Long dbQuantity = carRepertoryImpl.selectQuantity(car);

            if (dbQuantity <= 0) {
                rentalOrderDetail.setState(OrderDetailState.DB_QUANTITY_ILLEGAL);
                continue;
            }

            synchronized (CarRepertoryImpl.class) {

                dbQuantity = carRepertoryImpl.selectQuantity(car);
                // double check
                if (dbQuantity < 0) {
                    rentalOrderDetail.setState(OrderDetailState.DB_QUANTITY_ILLEGAL);
                    continue;
                }

                Long residueQuantity = dbQuantity - orderQuantity;

                if (residueQuantity < 0) {
                    rentalOrderDetail.setActualRentalQuantity(dbQuantity);
                } else {
                    rentalOrderDetail.setActualRentalQuantity(orderQuantity);
                }
                carRepertoryImpl.update(car, residueQuantity);
            }

            BigDecimal days = new BigDecimal(
                    Period.between(rentalOrderDetail.getFromDay(), rentalOrderDetail.getToDay()).getDays()
                            + 1
            );

            List<BigDecimal> nums = new ArrayList<>(4);

            nums.add(rentalOrderDetail.getDailyRent());
            nums.add(new BigDecimal(rentalOrderDetail.getOrderQuantity()));
            nums.add(days);

            rentalOrderDetail.setTotalAmount(CalculateUtils.multiplyLists(nums));
            rentalOrderDetail.setRealAmount(rentalOrderDetail.getTotalAmount().multiply(rentalOrderDetail.getDiscount()));
            rentalOrderDetail.setDiscountAmount(rentalOrderDetail.getTotalAmount().subtract(rentalOrderDetail.getRealAmount()));
        }


        List<RentalOrderDetail> correctOrderDetails = rentalOrderDetails
                .parallelStream()
                .filter(x -> x.getState() == OrderDetailState.NORMAL)
                .collect(Collectors.toList());

        if (correctOrderDetails.isEmpty()) {
            rentalOrderRes.setState(RentalOrderState.ORDER_DETAIL_ALL_EXCEPTION);
            log.error(RentalOrderState.ORDER_DETAIL_ALL_EXCEPTION.toString());
            return rentalOrderRes;
        }

        rentalOrderRes.setTotalAmount(OrderDetailUtils.calculateTotalAmount(correctOrderDetails));
        rentalOrderRes.setDiscountAmount(OrderDetailUtils.calculateDiscountAmount(correctOrderDetails));
        rentalOrderRes.setRealAmount(OrderDetailUtils.calculateRealAmount(correctOrderDetails));

        Map<Car, Long> updateCarQuantityMap = OrderDetailUtils.getBorrowerRecordFromOrderDetailsTemplate(
                correctOrderDetails,
                GET_ACTUAL_RENTAL_QUANTITY_METHOD_NAME);
        borrowerRepertory.update(rentalOrderReq.getBorrower(), updateCarQuantityMap);

        rentalOrderRes.setState(RentalOrderState.NORMAL);
        return rentalOrderRes;
    }

    @PostMapping("back/cars")
    public BackOrderRes backCar(@RequestBody BackOrderReq backOrderReq) {

        BackOrderRes backOrderRes = new BackOrderRes();

        if (backOrderReq == null) {
            backOrderRes.setState(BackOrderState.BACK_ORDER_IS_NULL);
            log.error(BackOrderState.BACK_ORDER_IS_NULL.toString());
            return backOrderRes;
        }

        BeanUtils.copyProperties(backOrderReq, backOrderRes);

        if (backOrderReq.getBorrower() == null) {
            backOrderRes.setState(BackOrderState.BORROWER_IS_NULL);
            log.error(BackOrderState.BORROWER_IS_NULL.toString());
            return backOrderRes;
        }

        List<BackOrderDetail> backOrderDetails = backOrderRes.getBackOrderDetailList();
        if (backOrderDetails == null || backOrderDetails.isEmpty()) {
            backOrderRes.setState(BackOrderState.BACK_ORDER_DETAIL_IS_NULL);
            log.error(BackOrderState.BACK_ORDER_DETAIL_IS_NULL.toString());
            return backOrderRes;
        }

        for (BackOrderDetail backOrderDetail : backOrderDetails) {

            OrderDetailUtils.checkBackOrderAndSetState(backOrderDetail);
            if (backOrderDetail.getState() != OrderDetailState.NORMAL) {
                log.info("the back order detail state is invalid, the order detail is " + backOrderDetail);
                continue;
            }

            Car car = backOrderDetail.getCar();
            Long orderQuantity = backOrderDetail.getOrderQuantity();
            carRepertoryImpl.add(car, orderQuantity);
        }

        List<BackOrderDetail> correctBackOrderDetails = backOrderDetails
                .parallelStream()
                .filter(x -> x.getState() == OrderDetailState.NORMAL)
                .collect(Collectors.toList());

        if (correctBackOrderDetails.isEmpty()) {
            backOrderRes.setState(BackOrderState.ALL_BACK_ORDER_DETAIL_IS_EXCEPTION);
            log.error(BackOrderState.BORROWER_IS_NULL.toString());
            return backOrderRes;
        }

        Map<Car, Long> updateCarQuantityMap = OrderDetailUtils.getBorrowerRecordFromOrderDetailsTemplate(
                correctBackOrderDetails,
                GET_ORDER_QUANTITY_METHOD_NAME);
        borrowerRepertory.update(backOrderReq.getBorrower(), updateCarQuantityMap);

        backOrderRes.setState(BackOrderState.NORMAL);
        return backOrderRes;
    }


    @GetMapping("hello")
    public String Hello() {
        return "Hello,Ordi.";
    }
}
