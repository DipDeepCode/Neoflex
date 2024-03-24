package ru.ddc.vacationpaycalculator.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ddc.vacationpaycalculator.config.VcpConfig;
import ru.ddc.vacationpaycalculator.dto.VcpResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Stream;

import static java.math.RoundingMode.*;

@Service
public class VacationPayService {

    private final VcpConfig vcpConfig;

    @Autowired
    public VacationPayService(VcpConfig vcpConfig) {
        this.vcpConfig = vcpConfig;
    }

    public VcpResponse calculateVacationPay(BigDecimal averageMonthSalary, long numberOfDaysToBePayed) {

        averageMonthSalary = averageMonthSalary.setScale(12, HALF_UP);
        BigDecimal averageDaysInMonth = vcpConfig.getAverageNumberOfDaysInMonth();
        BigDecimal averageDaySalary = averageMonthSalary.divide(averageDaysInMonth, HALF_UP);
        BigDecimal vacationPay = averageDaySalary.multiply(BigDecimal.valueOf(numberOfDaysToBePayed)).setScale(2, DOWN);
        BigDecimal taxValue = vcpConfig.getPersonalIncomeTaxValueAsPercentage();
        BigDecimal tax = vacationPay.multiply(taxValue).divide(new BigDecimal("100"), DOWN);
        return new VcpResponse(vacationPay.subtract(tax));
    }

    public VcpResponse calculateVacationPay(BigDecimal averageMonthSalary, Integer vacationDuration,
                                           LocalDate vacationStartDate) {
        long numberOfDaysToBePayed = Stream
                .iterate(vacationStartDate, date -> date.plusDays(1))
                .filter(date -> !vcpConfig.getDaysThatNotPaid().contains(date))
                .limit(vacationDuration - 1)
                .count();
        return calculateVacationPay(averageMonthSalary, numberOfDaysToBePayed);
    }
}
