package ru.ddc.vacationpaycalculator.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import ru.ddc.vacationpaycalculator.exceptions.SetConfigValueException;

import java.math.BigDecimal;

@Configuration
public class VcpConfig {
    private BigDecimal averageNumberOfDaysInMonth;
    private BigDecimal personalIncomeTaxValueAsPercentage;
    private final Environment environment;

    @Autowired
    public VcpConfig(Environment environment) {
        this.environment = environment;
    }

    @PostConstruct
    public void setDefaultValues() {
        setAverageNumberOfDaysInMonth(
                environment.getProperty("vcp.averageNumberOfDaysInMonth", "29.3"));
        setPersonalIncomeTaxValueAsPercentage(
                environment.getProperty("vcp.personalIncomeTaxValueAsPercentage", "13"));
    }

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

    public BigDecimal getAverageNumberOfDaysInMonth() {
        return new BigDecimal(averageNumberOfDaysInMonth.toString());
    }

    public BigDecimal getPersonalIncomeTaxValueAsPercentage() {
        return new BigDecimal(personalIncomeTaxValueAsPercentage.toString());
    }
}
