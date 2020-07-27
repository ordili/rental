package com.chenbao.rental.controller;


import com.alibaba.fastjson.JSON;
import com.chenbao.rental.model.entity.Car;
import com.chenbao.rental.model.entity.CarReq;
import com.chenbao.rental.model.entity.CarRes;
import com.chenbao.rental.model.entity.CarResState;
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

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
public class CarRepertoryControllerTest {

    private Logger log = LoggerFactory.getLogger(CarRepertoryControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testViewCarsAll() throws Exception {

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/view/cars/all")
                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(jsonString)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print()).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        int status = response.getStatus();
        assertThat(status).isEqualTo(200);

        log.info(response.getContentAsString());

        CarRes carRes = JSON.parseObject(response.getContentAsString(), CarRes.class);
        assertThat(carRes.getState()).isEqualByComparingTo(CarResState.NORMAL);

        Map<Car, Long> carQuantityMap = carRes.getCarQuantityMap();

        assertThat(carQuantityMap.size()).isEqualTo(2);
        Car car = Car.Builder().carModel("BMW 650");
        assertThat(carQuantityMap.containsKey(car)).isTrue();
        assertThat(carQuantityMap.get(car)).isEqualTo(2);
    }


    @Test
    public void testAddCar() throws Exception {

        CarReq carReq = new CarReq();
        Car car = Car.Builder().carModel("BMW");

        carReq.setCar(car);
        carReq.setQuantity(2L);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/add/car")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(carReq.toString())
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print()).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        int status = response.getStatus();
        assertThat(status).isEqualTo(200);

        log.info(response.getContentAsString());

        CarRes carRes = JSON.parseObject(response.getContentAsString(), CarRes.class);
        assertThat(carRes.getState()).isEqualByComparingTo(CarResState.NORMAL);

    }

}
