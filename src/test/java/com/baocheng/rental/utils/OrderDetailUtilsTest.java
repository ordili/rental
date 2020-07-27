package com.baocheng.rental.utils;

import com.baocheng.rental.model.entity.Car;
import com.baocheng.rental.model.order.BackOrderDetail;
import com.baocheng.rental.model.order.OrderDetailState;
import com.baocheng.rental.model.order.RentalOrderDetail;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class OrderDetailUtilsTest {

    private static Logger log = LoggerFactory.getLogger(OrderDetailUtilsTest.class);

    @Test
    public void testGetBorrowerRecordTemplateForRentalOrderDetail() {
        List<RentalOrderDetail> rentalOrderDetails = new ArrayList<>();

        RentalOrderDetail rentalOrderDetail = new RentalOrderDetail();

        Car car = Car.Builder().carModel("BMW 300");
        rentalOrderDetail.setCar(car);
        rentalOrderDetail.setState(OrderDetailState.NORMAL);
        rentalOrderDetail.setActualRentalQuantity(20L);

        rentalOrderDetails.add(rentalOrderDetail);
        Map<Car, Long> maps = OrderDetailUtils.getBorrowerRecordFromOrderDetailsTemplate(
                rentalOrderDetails,
                "getActualRentalQuantity");

        assertThat(maps).isNotNull();
        assertThat(maps).containsKeys(car);
        assertThat(maps.get(car)).isEqualTo(20L);

        log.info("" + maps);

    }

    @Test
    public void testGetBorrowerRecordTemplateForRentalOrderDetail2() {
        List<RentalOrderDetail> rentalOrderDetails = new ArrayList<>();

        RentalOrderDetail rentalOrderDetail = new RentalOrderDetail();

        Car car = Car.Builder().carModel("BMW 300");
        rentalOrderDetail.setCar(car);
        rentalOrderDetail.setState(OrderDetailState.NORMAL);
        rentalOrderDetail.setActualRentalQuantity(20L);
        rentalOrderDetails.add(rentalOrderDetail);

        RentalOrderDetail rentalOrderDetail2 = new RentalOrderDetail();

        Car car2 = Car.Builder().carModel("BMW");
        rentalOrderDetail2.setCar(car2);
        rentalOrderDetail2.setState(OrderDetailState.NORMAL);
        rentalOrderDetail2.setActualRentalQuantity(20L);
        rentalOrderDetails.add(rentalOrderDetail2);


        RentalOrderDetail rentalOrderDetail3 = new RentalOrderDetail();

        Car car3 = Car.Builder().carModel("BMW");
        rentalOrderDetail3.setCar(car2);
        rentalOrderDetail3.setState(OrderDetailState.NORMAL);
        rentalOrderDetail3.setActualRentalQuantity(20L);
        rentalOrderDetails.add(rentalOrderDetail3);

        RentalOrderDetail rentalOrderDetail4 = new RentalOrderDetail();

        Car car4 = Car.Builder().carModel("BMW");
        rentalOrderDetail4.setCar(car4);
        rentalOrderDetail4.setState(OrderDetailState.ORDER_DETAIL_IS_NULL);
        rentalOrderDetail4.setActualRentalQuantity(20L);
        rentalOrderDetails.add(rentalOrderDetail4);


        Map<Car, Long> maps = OrderDetailUtils.getBorrowerRecordFromOrderDetailsTemplate(
                rentalOrderDetails,
                "getActualRentalQuantity");

        assertThat(maps).isNotNull();
        assertThat(maps).containsKeys(car);
        assertThat(maps.get(car)).isEqualTo(20L);

        assertThat(maps).containsKeys(car2);
        assertThat(maps.get(car2)).isEqualTo(40L);

        log.info("" + maps);

    }

    @Test
    public void testGetBorrowerRecordTemplateForBackOrderDetail() {
        List<BackOrderDetail> backOrderDetails = new ArrayList<>();

        BackOrderDetail backOrderDetail = new BackOrderDetail();

        Car car = Car.Builder().carModel("BMW 300");
        backOrderDetail.setCar(car);
        backOrderDetail.setState(OrderDetailState.NORMAL);
        backOrderDetail.setOrderQuantity(20L);

        backOrderDetails.add(backOrderDetail);
        Map<Car, Long> maps = OrderDetailUtils.getBorrowerRecordFromOrderDetailsTemplate(
                backOrderDetails,
                "getOrderQuantity");

        assertThat(maps).isNotNull();
        assertThat(maps).containsKeys(car);
        assertThat(maps.get(car)).isEqualTo(20L);

        log.info("" + maps);

    }


    @Test
    void testCheckFromDayAndToDayIsLegalEmpytObj() {

        RentalOrderDetail orderDetail = new RentalOrderDetail();
        boolean res = OrderDetailUtils.checkFromDayAndToDayIsLegal(orderDetail);
        assertThat(res).isFalse();
    }

    @Test
    void testCheckFromDayAndToDayIsLegalTrue() {

        RentalOrderDetail orderDetail = new RentalOrderDetail();
        boolean res = false;

        orderDetail.setFromDay(LocalDate.of(2020, 05, 23));
        orderDetail.setToDay(LocalDate.of(2020, 05, 23));
        res = OrderDetailUtils.checkFromDayAndToDayIsLegal(orderDetail);
        assertThat(res).isTrue();


        orderDetail.setFromDay(LocalDate.of(2020, 03, 23));
        orderDetail.setToDay(LocalDate.of(2020, 03, 24));
        res = OrderDetailUtils.checkFromDayAndToDayIsLegal(orderDetail);
        assertThat(res).isTrue();
    }

    @Test
    void testCheckFromDayAndToDayIsLegalFalse() {

        RentalOrderDetail orderDetail = new RentalOrderDetail();
        boolean res = false;

        res = OrderDetailUtils.checkFromDayAndToDayIsLegal(null);
        assertThat(res).isFalse();

        orderDetail.setFromDay(LocalDate.of(2020, 05, 23));
        res = OrderDetailUtils.checkFromDayAndToDayIsLegal(orderDetail);
        assertThat(res).isFalse();

        orderDetail.setFromDay(null);
        orderDetail.setToDay(LocalDate.of(2020, 05, 24));
        res = OrderDetailUtils.checkFromDayAndToDayIsLegal(orderDetail);
        assertThat(res).isFalse();


        orderDetail.setFromDay(LocalDate.of(2020, 05, 26));
        orderDetail.setToDay(LocalDate.of(2020, 05, 24));
        res = OrderDetailUtils.checkFromDayAndToDayIsLegal(orderDetail);
        assertThat(res).isFalse();
    }
}
