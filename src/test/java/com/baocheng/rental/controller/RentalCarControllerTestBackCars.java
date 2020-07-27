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

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
public class RentalCarControllerTestBackCars {

    private Logger log = LoggerFactory.getLogger(RentalCarControllerTestBackCars.class);

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testBackCarNormal() throws Exception {

        BackOrderReq backOrderReq = new BackOrderReq();

        Borrower borrower = new Borrower();
        borrower.setName("ordi");
        backOrderReq.setBorrower(borrower);

        List<BackOrderDetail> backOrderDetails = new ArrayList<>();

        BackOrderDetail orderDetail = new BackOrderDetail();
        Car car = Car.Builder().carModel("BMW 650");

        orderDetail.setCar(car);
        orderDetail.setOrderQuantity(2L);

        backOrderDetails.add(orderDetail);

        backOrderReq.setBackOrderDetailList(backOrderDetails);

        String jsonString = JSONObject.toJSONString(backOrderReq);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/back/cars")
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

        BackOrderRes backOrderRes = JSON.parseObject(response.getContentAsString(), BackOrderRes.class);
        assertThat(backOrderRes.getState()).isEqualByComparingTo(BackOrderState.NORMAL);

    }

    @Test
    public void testBackCarException() throws Exception {

        BackOrderReq backOrderReq = new BackOrderReq();

        Borrower borrower = new Borrower();
        borrower.setName("ordi");
        backOrderReq.setBorrower(borrower);

        List<BackOrderDetail> backOrderDetails = new ArrayList<>();

        BackOrderDetail orderDetail = new BackOrderDetail();
        Car car = Car.Builder().carModel("BMW 650");

//        orderDetail.setCar(car);
        orderDetail.setOrderQuantity(2L);

        backOrderDetails.add(orderDetail);

        backOrderReq.setBackOrderDetailList(backOrderDetails);

        String jsonString = JSONObject.toJSONString(backOrderReq);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/back/cars")
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

        BackOrderRes backOrderRes = JSON.parseObject(response.getContentAsString(), BackOrderRes.class);
        assertThat(backOrderRes.getState()).isNotEqualByComparingTo(BackOrderState.NORMAL);

    }

    @Test
    public void testBackCarException_2() throws Exception {

        BackOrderReq backOrderReq = new BackOrderReq();

        Borrower borrower = new Borrower();
        borrower.setName("ordi");
        backOrderReq.setBorrower(borrower);

        List<BackOrderDetail> backOrderDetails = new ArrayList<>();

        BackOrderDetail orderDetail = new BackOrderDetail();
        Car car = Car.Builder().carModel("BMW 650");

        orderDetail.setCar(car);
//        orderDetail.setOrderQuantity(2L);

        backOrderDetails.add(orderDetail);

        backOrderReq.setBackOrderDetailList(backOrderDetails);

        String jsonString = JSONObject.toJSONString(backOrderReq);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/back/cars")
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

        BackOrderRes backOrderRes = JSON.parseObject(response.getContentAsString(), BackOrderRes.class);
        assertThat(backOrderRes.getState()).isNotEqualByComparingTo(BackOrderState.NORMAL);

    }


    @Test
    public void testBackCarException_3() throws Exception {

        BackOrderReq backOrderReq = new BackOrderReq();

        Borrower borrower = new Borrower();
        borrower.setName("ordi");
        backOrderReq.setBorrower(borrower);

        List<BackOrderDetail> backOrderDetails = new ArrayList<>();

        BackOrderDetail orderDetail = new BackOrderDetail();
        Car car = Car.Builder().carModel("BMW 650");

        orderDetail.setCar(car);
        orderDetail.setOrderQuantity(2L);

//        backOrderDetails.add(orderDetail);

        backOrderReq.setBackOrderDetailList(backOrderDetails);

        String jsonString = JSONObject.toJSONString(backOrderReq);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/back/cars")
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

        BackOrderRes backOrderRes = JSON.parseObject(response.getContentAsString(), BackOrderRes.class);
        assertThat(backOrderRes.getState()).isNotEqualByComparingTo(BackOrderState.NORMAL);

    }


    @Test
    public void testBackCarException_4() throws Exception {

        BackOrderReq backOrderReq = new BackOrderReq();

        Borrower borrower = new Borrower();
        borrower.setName("ordi");
        backOrderReq.setBorrower(borrower);

        List<BackOrderDetail> backOrderDetails = new ArrayList<>();

        backOrderReq.setBackOrderDetailList(null);

        String jsonString = JSONObject.toJSONString(backOrderReq);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/back/cars")
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

        BackOrderRes backOrderRes = JSON.parseObject(response.getContentAsString(), BackOrderRes.class);
        assertThat(backOrderRes.getState()).isNotEqualByComparingTo(BackOrderState.NORMAL);

    }

    @Test
    public void testBackCarException_5() throws Exception {

        BackOrderReq backOrderReq = new BackOrderReq();

        Borrower borrower = new Borrower();
        borrower.setName("ordi");
        backOrderReq.setBorrower(borrower);

        List<BackOrderDetail> backOrderDetails = new ArrayList<>();

        backOrderReq.setBackOrderDetailList(backOrderDetails);

        String jsonString = JSONObject.toJSONString(backOrderReq);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/back/cars")
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

        BackOrderRes backOrderRes = JSON.parseObject(response.getContentAsString(), BackOrderRes.class);
        assertThat(backOrderRes.getState()).isNotEqualByComparingTo(BackOrderState.NORMAL);

    }

    @Test
    public void testBackCarException_6() throws Exception {

        BackOrderReq backOrderReq = new BackOrderReq();

        Borrower borrower = new Borrower();
        borrower.setName("ordi");
//        backOrderReq.setBorrower(borrower);

        List<BackOrderDetail> backOrderDetails = new ArrayList<>();

        BackOrderDetail orderDetail = new BackOrderDetail();
        Car car = Car.Builder().carModel("BMW 650");

        orderDetail.setCar(car);
        orderDetail.setOrderQuantity(2L);

        backOrderDetails.add(orderDetail);

        backOrderReq.setBackOrderDetailList(backOrderDetails);

        String jsonString = JSONObject.toJSONString(backOrderReq);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/back/cars")
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

        BackOrderRes backOrderRes = JSON.parseObject(response.getContentAsString(), BackOrderRes.class);
        assertThat(backOrderRes.getState()).isNotEqualByComparingTo(BackOrderState.NORMAL);

    }

    @Test
    public void testBackCarException_7() throws Exception {

        BackOrderReq backOrderReq = new BackOrderReq();
        String jsonString = JSONObject.toJSONString(backOrderReq);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/back/cars")
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

        BackOrderRes backOrderRes = JSON.parseObject(response.getContentAsString(), BackOrderRes.class);
        assertThat(backOrderRes.getState()).isNotEqualByComparingTo(BackOrderState.NORMAL);

    }

}
