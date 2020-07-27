package com.chenbao.rental.model.order;

import com.alibaba.fastjson.JSONObject;
import com.chenbao.rental.model.entity.Car;
import lombok.Data;

import java.util.Objects;


@Data
public class BorrowerCar {

    private Car car;
    private Long quantity;

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BorrowerCar)) {
            return false;
        }

        BorrowerCar other = (BorrowerCar) obj;

        return Objects.equals(car, other.car);
    }

    @Override
    public int hashCode() {
        return car == null ? 0 : car.hashCode();
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
