package ru.ddc.vacationpaycalculator.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import ru.ddc.vacationpaycalculator.exceptions.SetConfigValueException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "vcp")
public class VcpConfig {
    private BigDecimal averageNumberOfDaysInMonth;
    private BigDecimal personalIncomeTaxValueAsPercentage;
    private List<LocalDate> daysThatNotPaid;

    public void setAverageNumberOfDaysInMonth(String value) {
        BigDecimal averageNumberOfDaysInMonth;
        try {
            averageNumberOfDaysInMonth = new BigDecimal(value);
        } catch (NumberFormatException e) {
            throw new SetConfigValueException("Incorrect value: " + value);
        }
        if (averageNumberOfDaysInMonth.compareTo(new BigDecimal("0")) < 0 ||
                averageNumberOfDaysInMonth.compareTo(new BigDecimal("31")) > 0) {
            throw new SetConfigValueException("Incorrect value: " + value);
        }
        this.averageNumberOfDaysInMonth = averageNumberOfDaysInMonth;
    }

    public BigDecimal getAverageNumberOfDaysInMonth() {
        return new BigDecimal(averageNumberOfDaysInMonth.toString());
    }

    public void setPersonalIncomeTaxValueAsPercentage(String value) {
        BigDecimal personalIncomeTaxValueAsPercentage;
        try {
            personalIncomeTaxValueAsPercentage = new BigDecimal(value);
        } catch (NumberFormatException e) {
            throw new SetConfigValueException("Incorrect value: " + value);
        }
        if (personalIncomeTaxValueAsPercentage.compareTo(new BigDecimal("0")) < 0 ||
                personalIncomeTaxValueAsPercentage.compareTo(new BigDecimal("100")) > 0) {
            throw new SetConfigValueException("Incorrect value: " + value);
        }
        this.personalIncomeTaxValueAsPercentage = personalIncomeTaxValueAsPercentage;
    }

    public BigDecimal getPersonalIncomeTaxValueAsPercentage() {
        return new BigDecimal(personalIncomeTaxValueAsPercentage.toString());
    }

    public void setDaysThatNotPaid(List<LocalDate> daysThatNotPaid) {
        this.daysThatNotPaid = daysThatNotPaid;
    }

    public void addDaysThatNotPaid(List<LocalDate> daysThatNotPaid) {
        this.daysThatNotPaid.addAll(daysThatNotPaid);
    }

    public void removeDaysThatNotPaid(List<LocalDate> daysThatNotPaid) {
        this.daysThatNotPaid.removeAll(daysThatNotPaid);
    }

    public List<LocalDate> getDaysThatNotPaid() {
        return Collections.unmodifiableList(daysThatNotPaid);
    }
}
