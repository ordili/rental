package com.chenbao.rental.repertory;

import com.chenbao.rental.model.entity.Car;
import com.chenbao.rental.repertory.operatioin.OperationI;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
@Data
public class CarRepertoryImpl implements OperationI<Car, Long> {

    private Logger log = LoggerFactory.getLogger(CarRepertoryImpl.class);

    private Map<Car, Long> repertory;

    public CarRepertoryImpl() {
        repertory = new HashMap<>(16);

        Car car1 = Car.Builder()
                .carModel("Toyota Camry");
        repertory.put(car1, 2L);

        Car car2 = Car.Builder()
                .carModel("BMW 650");

        repertory.put(car2, 2L);

        log.info("The car repertory is " + repertory);
    }

    @Override
    public void add(Car car, Long nums) {

        if (car == null || nums == null || nums <= 0L) {
            return;
        }

        synchronized (this) {

            if (repertory.containsKey(car)) {
                nums += repertory.get(car);
            }
            repertory.put(car, nums);
        }
    }

    @Override
    public void delete(Car car) {
        if (!repertory.containsKey(car)) {
            return;
        }
        synchronized (this) {
            repertory.remove(car);
        }
    }

    @Override
    public Long selectQuantity(Car car) {

        if(car == null){
            return 0L;
        }

        Long ret = 0L;
        synchronized (this) {
            if (repertory.containsKey(car)) {
                ret = repertory.get(car);
            }
        }
        return ret;
    }

    @Override
    public void update(Car car, Long nums) {
        if (!repertory.containsKey(car)) {
            return;
        }
        synchronized (this) {

            if (repertory.containsKey(car)) {

                if (nums < 0) {
                    nums = 0L;
                }

                repertory.put(car, nums);

            }
        }
    }

    @Override
    public boolean contains(Car car) {
        if (!repertory.containsKey(car)) {
            return false;
        }
        return repertory.get(car) > 0 ? true : false;
    }
}
