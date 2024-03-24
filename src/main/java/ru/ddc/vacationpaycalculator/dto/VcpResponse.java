package ru.ddc.vacationpaycalculator.dto;

import java.math.BigDecimal;

public class VcpResponse {
    private BigDecimal result;

    public VcpResponse(BigDecimal result) {
        this.result = result;
    }

    public BigDecimal getResult() {
        return result;
    }

    public void setResult(BigDecimal result) {
        this.result = result;
    }
}
