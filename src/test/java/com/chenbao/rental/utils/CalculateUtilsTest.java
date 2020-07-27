package com.chenbao.rental.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class CalculateUtilsTest {

    @Test
    void testMultiplyListsTestNull() {

        BigDecimal res = CalculateUtils.multiplyLists(null);
        assertThat(res).isNull();
    }

    @Test
    void testMultiplyListsTestEmptyList() {

        BigDecimal res = CalculateUtils.multiplyLists(new ArrayList<>());
        assertThat(res).isNull();
    }

    @Test
    void testMultiplyListsTestContainsNull() {

        List<BigDecimal> lists = new ArrayList<BigDecimal>();

        BigDecimal a = new BigDecimal(4);
        BigDecimal b = new BigDecimal(5);
        BigDecimal c = new BigDecimal(6);

        lists.add(a);
        lists.add(b);
        lists.add(c);
        lists.add(null);

        BigDecimal res = CalculateUtils.multiplyLists(lists);

        assertThat(res).isNull();
    }

    @Test
    void testMultiplyListsTestContainsZero() {

        List<BigDecimal> lists = new ArrayList<BigDecimal>();

        BigDecimal a = new BigDecimal(4);
        BigDecimal b = new BigDecimal(5);
        BigDecimal c = new BigDecimal(6);

        lists.add(a);
        lists.add(b);
        lists.add(c);
        lists.add(BigDecimal.ZERO);

        BigDecimal res = CalculateUtils.multiplyLists(lists);
        assertThat(res).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    void testMultiplyListsTestRight() {

        List<BigDecimal> lists = new ArrayList<BigDecimal>();

        BigDecimal a = new BigDecimal(4);
        BigDecimal b = new BigDecimal(5);
        BigDecimal c = new BigDecimal(6);

        lists.add(a);
        lists.add(b);
        lists.add(c);

        BigDecimal res = CalculateUtils.multiplyLists(lists);

        assertThat(res).isEqualTo(a.multiply(b).multiply(c));
    }

}
