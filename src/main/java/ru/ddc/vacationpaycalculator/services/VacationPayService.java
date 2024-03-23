package ru.ddc.vacationpaycalculator.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ddc.vacationpaycalculator.config.VcpConfig;

import java.math.BigDecimal;

import static java.math.RoundingMode.*;

@Service
public class VacationPayService {

    private final VcpConfig vcpConfig;

    @Autowired
    public VacationPayService(VcpConfig vcpConfig) {
        this.vcpConfig = vcpConfig;
    }

    public BigDecimal calculateVacationPay(BigDecimal averageMonthSalary, Integer vacationDuration) {
        averageMonthSalary = averageMonthSalary.setScale(12, HALF_UP);
        BigDecimal averageDaysInMonth = vcpConfig.getAverageNumberOfDaysInMonth();
        BigDecimal averageDaySalary = averageMonthSalary.divide(averageDaysInMonth, HALF_UP);
        BigDecimal vacationPay = averageDaySalary.multiply(BigDecimal.valueOf(vacationDuration)).setScale(2, DOWN);
        BigDecimal taxValue = vcpConfig.getPersonalIncomeTaxValueAsPercentage();
        BigDecimal tax = vacationPay.multiply(taxValue).divide(new BigDecimal("100"), DOWN);
        return vacationPay.subtract(tax);
    }
}
