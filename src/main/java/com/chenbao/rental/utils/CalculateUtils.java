package com.chenbao.rental.utils;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class CalculateUtils {

    /**
     * Calculate the product of N numbers of BigDecimal.
     *
     * @param bigDecimals
     * @return return null if some BigDecimal is null or bigDecimals is null or empty.
     */
    public static BigDecimal multiplyLists(List<BigDecimal> bigDecimals) {

        BigDecimal res;

        if (bigDecimals == null || bigDecimals.isEmpty()) {
            return null;
        }

        List<BigDecimal> nulls = bigDecimals.stream().filter(x -> x == null).collect(Collectors.toList());

        if (!nulls.isEmpty()) {
            return null;
        }

        res = bigDecimals.parallelStream().reduce(BigDecimal.ONE, BigDecimal::multiply);

        return res;
    }

}
