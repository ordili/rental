package com.baocheng.rental.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baocheng.rental.model.entity.Car;
import com.baocheng.rental.model.order.*;
import com.baocheng.rental.model.order.*;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;

import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
public class RentalCarControllerTestRentCars {

    private Logger log = LoggerFactory.getLogger(RentalCarControllerTestRentCars.class);

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testRentalCarNormal() throws Exception {

        RentalOrderReq rentalOrderReq = new RentalOrderReq();

        Borrower borrower = new Borrower();
        borrower.setName("ordi");
        rentalOrderReq.setBorrower(borrower);

        List<RentalOrderDetail> rentalOrderDetails = new ArrayList<>();

        RentalOrderDetail orderDetail = new RentalOrderDetail();
        Car car = Car.Builder().carModel("BMW 650");

        orderDetail.setCar(car);
        orderDetail.setOrderQuantity(2L);
        orderDetail.setFromDay(LocalDate.of(2020, 03, 23));
        orderDetail.setToDay(LocalDate.of(2020, 03, 24));
        orderDetail.setDailyRent(BigDecimal.ONE);
        rentalOrderDetails.add(orderDetail);

        rentalOrderReq.setRentalOrderDetailList(rentalOrderDetails);

        String jsonString = JSONObject.toJSONString(rentalOrderReq);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/rent/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print()).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        int status = response.getStatus();
        assertThat(status).isEqualTo(200);

        log.info(response.getContentAsString());
        RentalOrderRes rentalOrderRes = JSON.parseObject(response.getContentAsString(), RentalOrderRes.class);
        assertThat(rentalOrderRes.getState()).isEqualByComparingTo(RentalOrderState.NORMAL);
        assertThat(rentalOrderRes.getTotalAmount()).isEqualTo(BigDecimal.valueOf(4));

    }

    @Test
    public void testRentalCarInputNull() throws Exception {

        RentalOrderReq rentalOrderReq = new RentalOrderReq();
        String jsonString = JSONObject.toJSONString(rentalOrderReq);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/rent/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print()).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        int status = response.getStatus();
        assertThat(status).isEqualTo(200);

        log.info(response.getContentAsString());
        RentalOrderRes rentalOrderRes = JSON.parseObject(response.getContentAsString(), RentalOrderRes.class);
        assertThat(rentalOrderRes.getState()).isNotEqualByComparingTo(RentalOrderState.NORMAL);

    }

       @Test
    public void testRentalCarException_1() throws Exception {

        RentalOrderReq rentalOrderReq = new RentalOrderReq();

        Borrower borrower = new Borrower();
        borrower.setName("ordi");
        rentalOrderReq.setBorrower(borrower);

        List<RentalOrderDetail> rentalOrderDetails = new ArrayList<>();

        RentalOrderDetail orderDetail = new RentalOrderDetail();
        Car car = Car.Builder().carModel("BMW 650");

        orderDetail.setCar(car);
        orderDetail.setOrderQuantity(2L);
        orderDetail.setFromDay(LocalDate.of(2020, 03, 23));
        orderDetail.setToDay(LocalDate.of(2020, 03, 24));
//        orderDetail.setDailyRent(BigDecimal.ONE);

        rentalOrderDetails.add(orderDetail);

        rentalOrderReq.setRentalOrderDetailList(rentalOrderDetails);

        String jsonString = JSONObject.toJSONString(rentalOrderReq);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/rent/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print()).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        int status = response.getStatus();
        assertThat(status).isEqualTo(200);

        log.info(response.getContentAsString());
        RentalOrderRes rentalOrderRes = JSON.parseObject(response.getContentAsString(), RentalOrderRes.class);
        assertThat(rentalOrderRes.getState()).isNotEqualByComparingTo(RentalOrderState.NORMAL);

    }

      @Test
    public void testRentalCarException_2() throws Exception {

        RentalOrderReq rentalOrderReq = new RentalOrderReq();

        Borrower borrower = new Borrower();
        borrower.setName("ordi");
        rentalOrderReq.setBorrower(borrower);

        List<RentalOrderDetail> rentalOrderDetails = new ArrayList<>();

        RentalOrderDetail orderDetail = new RentalOrderDetail();
        Car car = Car.Builder().carModel("BMW 650");

        orderDetail.setCar(car);
        orderDetail.setOrderQuantity(2L);
        orderDetail.setFromDay(LocalDate.of(2020, 03, 23));
        orderDetail.setToDay(LocalDate.of(2020, 03, 24));
        orderDetail.setDailyRent(BigDecimal.ONE);

        rentalOrderDetails.add(orderDetail);

//        rentalOrderReq.setRentalOrderDetailList(rentalOrderDetails);

        String jsonString = JSONObject.toJSONString(rentalOrderReq);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/rent/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print()).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        int status = response.getStatus();
        assertThat(status).isEqualTo(200);

        log.info(response.getContentAsString());
        RentalOrderRes rentalOrderRes = JSON.parseObject(response.getContentAsString(), RentalOrderRes.class);
        assertThat(rentalOrderRes.getState()).isNotEqualByComparingTo(RentalOrderState.NORMAL);

    }

    @Test
    public void testRentalCarException_3() throws Exception {

        RentalOrderReq rentalOrderReq = new RentalOrderReq();

        Borrower borrower = new Borrower();
        borrower.setName("ordi");
        rentalOrderReq.setBorrower(borrower);

        List<RentalOrderDetail> rentalOrderDetails = new ArrayList<>();

        RentalOrderDetail orderDetail = new RentalOrderDetail();
        Car car = Car.Builder().carModel("BMW 650");

        orderDetail.setCar(car);
        orderDetail.setOrderQuantity(2L);
        orderDetail.setFromDay(LocalDate.of(2020, 03, 23));
        orderDetail.setToDay(LocalDate.of(2020, 03, 24));
        orderDetail.setDailyRent(BigDecimal.ONE);

//        rentalOrderDetails.add(orderDetail);

        rentalOrderReq.setRentalOrderDetailList(rentalOrderDetails);

        String jsonString = JSONObject.toJSONString(rentalOrderReq);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/rent/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print()).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        int status = response.getStatus();
        assertThat(status).isEqualTo(200);

        log.info(response.getContentAsString());
        RentalOrderRes rentalOrderRes = JSON.parseObject(response.getContentAsString(), RentalOrderRes.class);
        assertThat(rentalOrderRes.getState()).isNotEqualByComparingTo(RentalOrderState.NORMAL);

    }


    @Test
    public void testRentalCarException_4() throws Exception {

        RentalOrderReq rentalOrderReq = new RentalOrderReq();

        Borrower borrower = new Borrower();
        borrower.setName("ordi");
        rentalOrderReq.setBorrower(borrower);

        List<RentalOrderDetail> rentalOrderDetails = new ArrayList<>();

        RentalOrderDetail orderDetail = new RentalOrderDetail();
        Car car = Car.Builder().carModel("BMW 650");

        orderDetail.setCar(car);
        orderDetail.setOrderQuantity(2L);
        orderDetail.setFromDay(LocalDate.of(2020, 03, 23));
        orderDetail.setToDay(LocalDate.of(2020, 03, 22));
        orderDetail.setDailyRent(BigDecimal.ONE);

        rentalOrderDetails.add(orderDetail);

        rentalOrderReq.setRentalOrderDetailList(rentalOrderDetails);

        String jsonString = JSONObject.toJSONString(rentalOrderReq);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/rent/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print()).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        int status = response.getStatus();
        assertThat(status).isEqualTo(200);

        log.info(response.getContentAsString());
        RentalOrderRes rentalOrderRes = JSON.parseObject(response.getContentAsString(), RentalOrderRes.class);
        assertThat(rentalOrderRes.getState()).isNotEqualByComparingTo(RentalOrderState.NORMAL);

    }


       @Test
    public void testRentalCarException_5() throws Exception {

        RentalOrderReq rentalOrderReq = new RentalOrderReq();

        Borrower borrower = new Borrower();
        borrower.setName("ordi");
        rentalOrderReq.setBorrower(borrower);

        List<RentalOrderDetail> rentalOrderDetails = new ArrayList<>();

        RentalOrderDetail orderDetail = new RentalOrderDetail();
        Car car = Car.Builder().carModel("BMW 650");

        orderDetail.setCar(car);
//        orderDetail.setOrderQuantity(2L);
        orderDetail.setFromDay(LocalDate.of(2020, 03, 23));
        orderDetail.setToDay(LocalDate.of(2020, 03, 24));
        orderDetail.setDailyRent(BigDecimal.ONE);

        rentalOrderDetails.add(orderDetail);

        rentalOrderReq.setRentalOrderDetailList(rentalOrderDetails);

        String jsonString = JSONObject.toJSONString(rentalOrderReq);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/rent/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print()).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        int status = response.getStatus();
        assertThat(status).isEqualTo(200);

        log.info(response.getContentAsString());
        RentalOrderRes rentalOrderRes = JSON.parseObject(response.getContentAsString(), RentalOrderRes.class);
        assertThat(rentalOrderRes.getState()).isNotEqualByComparingTo(RentalOrderState.NORMAL);

    }


    @Test
    public void testRentalCarException_6() throws Exception {

        RentalOrderReq rentalOrderReq = new RentalOrderReq();

        Borrower borrower = new Borrower();
        borrower.setName("ordi");
        rentalOrderReq.setBorrower(borrower);

        List<RentalOrderDetail> rentalOrderDetails = new ArrayList<>();

        RentalOrderDetail orderDetail = new RentalOrderDetail();
        Car car = Car.Builder().carModel("BMW 650");

//        orderDetail.setCar(car);
        orderDetail.setOrderQuantity(2L);
        orderDetail.setFromDay(LocalDate.of(2020, 03, 23));
        orderDetail.setToDay(LocalDate.of(2020, 03, 24));
        orderDetail.setDailyRent(BigDecimal.ONE);

        rentalOrderDetails.add(orderDetail);

        rentalOrderReq.setRentalOrderDetailList(rentalOrderDetails);

        String jsonString = JSONObject.toJSONString(rentalOrderReq);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/rent/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print()).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        int status = response.getStatus();
        assertThat(status).isEqualTo(200);

        log.info(response.getContentAsString());
        RentalOrderRes rentalOrderRes = JSON.parseObject(response.getContentAsString(), RentalOrderRes.class);
        assertThat(rentalOrderRes.getState()).isNotEqualByComparingTo(RentalOrderState.NORMAL);

    }


    @Test
    public void testRentalCarException_7() throws Exception {

        RentalOrderReq rentalOrderReq = new RentalOrderReq();

        Borrower borrower = new Borrower();
        borrower.setName("ordi");
//        rentalOrderReq.setBorrower(borrower);

        List<RentalOrderDetail> rentalOrderDetails = new ArrayList<>();

        RentalOrderDetail orderDetail = new RentalOrderDetail();
        Car car = Car.Builder().carModel("BMW 650");

        orderDetail.setCar(car);
        orderDetail.setOrderQuantity(2L);
        orderDetail.setFromDay(LocalDate.of(2020, 03, 23));
        orderDetail.setToDay(LocalDate.of(2020, 03, 24));
        orderDetail.setDailyRent(BigDecimal.ONE);

        rentalOrderDetails.add(orderDetail);

        rentalOrderReq.setRentalOrderDetailList(rentalOrderDetails);

        String jsonString = JSONObject.toJSONString(rentalOrderReq);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/rent/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print()).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        int status = response.getStatus();
        assertThat(status).isEqualTo(200);

        log.info(response.getContentAsString());
        RentalOrderRes rentalOrderRes = JSON.parseObject(response.getContentAsString(), RentalOrderRes.class);
        assertThat(rentalOrderRes.getState()).isNotEqualByComparingTo(RentalOrderState.NORMAL);

    }

}
