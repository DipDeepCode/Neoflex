package ru.ddc.vacationpaycalculator.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.ddc.vacationpaycalculator.services.VacationPayService;

import java.math.BigDecimal;

@RestController
@RequestMapping("")
public class VacationPayController {

    private final VacationPayService vacationPayService;

    @Autowired
    public VacationPayController(VacationPayService vacationPayService) {
        this.vacationPayService = vacationPayService;
    }

    @GetMapping("/calculate")
    public BigDecimal calculateVacationPay(@RequestParam(name = "averageSalary") BigDecimal averageSalary,
                                           @RequestParam(name = "vacationDuration") Integer vacationDuration) {
        return vacationPayService.calculateVacationPay(averageSalary, vacationDuration);
    }
}
