package com.chenbao.rental.model.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.Objects;

@Data
public class Car {

    private String carModel;

    public Car() {

    }

    public static Car Builder() {
        return new Car();
    }

    public Car carModel(String carModel) {
        this.carModel = carModel;
        return this;
    }

    @Override
    public int hashCode() {
        return carModel == null ? 0 : carModel.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Car)) {
            return false;
        }

        Car newCar = (Car) obj;

        return Objects.equals(newCar.carModel, this.carModel);
    }


    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
