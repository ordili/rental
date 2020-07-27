package com.baocheng.rental.controller;

import com.baocheng.rental.model.entity.Car;
import com.baocheng.rental.model.entity.CarReq;
import com.baocheng.rental.model.entity.CarRes;
import com.baocheng.rental.model.entity.CarResState;
import com.baocheng.rental.repertory.CarRepertoryImpl;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Data July 27, 2020
 * @Author Jidong Li
 */
@RestController
@RequestMapping
@Api(tags = {"CarRepertoryController"})
public class CarRepertoryController {

    private Logger log = LoggerFactory.getLogger(CarRepertoryController.class);

    @Autowired
    private CarRepertoryImpl carRepertoryImpl;

    @PostMapping("add/car")
    public CarRes addCar(@RequestBody CarReq car) {

        CarRes carRes = new CarRes();
        if (car == null) {
            carRes.setState(CarResState.CARS_IS_NULL);
            return carRes;
        }
        carRepertoryImpl.add(car.getCar(), car.getQuantity());
        carRes.setState(CarResState.NORMAL);

        return carRes;
    }

    @PostMapping("add/cars")
    public CarRes addCars(@RequestBody List<CarReq> carList) {

        CarRes carRes = new CarRes();
        if (carList == null || carList.isEmpty()) {
            carRes.setState(CarResState.CARS_IS_NULL);
            return carRes;
        }

        for (CarReq car : carList) {
            carRepertoryImpl.add(car.getCar(), car.getQuantity());
        }

        carRes.setState(CarResState.NORMAL);

        return carRes;
    }


    @PostMapping("view/car")
    public CarRes viewCar(@RequestBody Car car) {
        CarRes carRes = new CarRes();
        if (car == null) {
            carRes.setState(CarResState.CARS_IS_NULL);
            return carRes;
        }

        Map<Car, Long> carQuantityMap = new HashMap<>(2);
        carQuantityMap.put(car, carRepertoryImpl.selectQuantity(car));

        carRes.setState(CarResState.NORMAL);
        carRes.setCarQuantityMap(carQuantityMap);

        return carRes;
    }

    @PostMapping("view/cars")
    public CarRes viewCars(@RequestBody List<Car> carList) {

        CarRes carRes = new CarRes();

        if (carList == null || carList.isEmpty()) {
            carRes.setState(CarResState.CARS_IS_NULL);
            return carRes;
        }

        Map<Car, Long> carQuantityMap = new HashMap<>(carList.size());

        for (Car car : carList) {

            Long oldQuantity = carQuantityMap.getOrDefault(car, 0L);
            oldQuantity += carRepertoryImpl.selectQuantity(car);
            carQuantityMap.put(car, oldQuantity);
        }

        carRes.setCarQuantityMap(carQuantityMap);
        carRes.setState(CarResState.NORMAL);

        return carRes;
    }

    @GetMapping("view/cars/all")
    public CarRes viewCarsAll() {

        CarRes carRes = new CarRes();
        Map<Car, Long> carQuantityMap = new HashMap<>(carRepertoryImpl.getRepertory());
        carRes.setCarQuantityMap(carQuantityMap);
        carRes.setState(CarResState.NORMAL);
        return carRes;
    }

}
