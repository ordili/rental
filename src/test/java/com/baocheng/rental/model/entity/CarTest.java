package com.baocheng.rental.model.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class CarTest {

    @Test
    void testEquals() {
        Car car1 = Car.Builder().carModel("A");
        Car car2 = Car.Builder().carModel("A");

        assertThat(car1).isEqualTo(car2);
        assertThat(car1.equals(car2)).isEqualTo(true);
    }

    @Test
    void testEqualsEmptyStr() {
        Car car1 = Car.Builder().carModel("");
        Car car2 = Car.Builder().carModel("");
        assertThat(car1).isEqualTo(car2);
        assertThat(car1.equals(car2)).isEqualTo(true);
    }


    @Test
    void testEqualsEmptyStr2() {
        Car car1 = Car.Builder().carModel("  ");
        Car car2 = Car.Builder().carModel("  ");
        assertThat(car1).isEqualTo(car2);
        assertThat(car1.equals(car2)).isEqualTo(true);
    }

    @Test
    void testHashCode() {
        Car car1 = Car.Builder().carModel("A");
        Car car2 = Car.Builder().carModel("A");
        assertThat(car1.hashCode()).isEqualTo(car2.hashCode());
    }

    @Test
    void testHashCodeEmptyStr() {
        Car car1 = Car.Builder().carModel("");
        Car car2 = Car.Builder().carModel("");
        assertThat(car1.hashCode()).isEqualTo(car2.hashCode());
    }

    @Test
    void testHashCodeEmptyStr2() {
        Car car1 = Car.Builder().carModel("  ");
        Car car2 = Car.Builder().carModel("  ");
        assertThat(car1.hashCode()).isEqualTo(car2.hashCode());
    }
}
