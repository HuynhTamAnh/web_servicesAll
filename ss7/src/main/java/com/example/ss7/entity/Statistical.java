package com.example.ss7.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Statistical {

    public int countRemainingSeeds() {
        return 0;
    }

    public double totalHarvestMoneyThisMonth() {
        return 0.0;
    }

    public double totalPaymentSlipsThisMonth() {
        return 0.0;
    }

    public Map<String, Double> profitLossOverYear() {
        return null;
    }
}
