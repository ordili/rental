package com.baocheng.rental.repertory;

import com.baocheng.rental.model.entity.Car;
import com.baocheng.rental.model.order.Borrower;
import com.baocheng.rental.repertory.operatioin.OperationI;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@Data
public class BorrowerRepertoryImpl implements OperationI<Borrower, Map<Car, Long>> {

    private Logger log = LoggerFactory.getLogger(BorrowerRepertoryImpl.class);

    private Map<Borrower, Map<Car, Long>> repertory;

    public BorrowerRepertoryImpl() {
        repertory = new HashMap<>(16);
        log.info("The car repertory is " + repertory);
    }

    @Override
    public void add(Borrower borrower, Map<Car, Long> borrowerCar) {
        throw new RuntimeException("Don't support add operation");
    }

    @Override
    public void delete(Borrower obj) {

    }

    @Override
    public Long selectQuantity(Borrower obj) {
        return null;
    }

    @Override
    public void update(Borrower borrower, Map<Car, Long> updateCarQuantityMap) {

        if (borrower == null || updateCarQuantityMap == null || updateCarQuantityMap.isEmpty()) {
            return;
        }

        synchronized (this) {

            Map<Car, Long> beforeCarQuantityMap = repertory.getOrDefault(borrower, null);


            if (beforeCarQuantityMap == null || beforeCarQuantityMap.isEmpty()) {
                repertory.put(borrower, updateCarQuantityMap);
                log.info("insert borrower " + borrower + " with updateCarQuantityMap " + updateCarQuantityMap);
                return;
            }

            log.info("the borrower " + borrower + " has the rental record " + beforeCarQuantityMap);

            List<Car> updates = updateCarQuantityMap.keySet().stream()
                    .filter(x -> beforeCarQuantityMap.containsKey(x))
                    .collect(Collectors.toList());

            List<Car> adds = updateCarQuantityMap.keySet().stream()
                    .filter(x -> !beforeCarQuantityMap.containsKey(x))
                    .collect(Collectors.toList());

            for (Car car : updates) {
                Long quantity = beforeCarQuantityMap.get(car) + updateCarQuantityMap.get(car);
                if (quantity < 0L) {
                    quantity = 0L;
                }
                beforeCarQuantityMap.put(car, quantity);
            }

            for (Car car : adds) {
                Long quantity = updateCarQuantityMap.get(car);
                if (quantity < 0) {
                    quantity = 0L;
                }
                beforeCarQuantityMap.put(car, quantity);
            }


            repertory.put(borrower, beforeCarQuantityMap);
            log.info("the borrower " + borrower + " updated the rental record " + beforeCarQuantityMap);

        }
    }

    @Override
    public boolean contains(Borrower obj) {
        return repertory.containsKey(obj);
    }
}
