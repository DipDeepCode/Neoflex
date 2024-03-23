package ru.ddc.vacationpaycalculator.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.ddc.vacationpaycalculator.services.VacationPayService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("")
public class VacationPayController {

    private final VacationPayService vacationPayService;

    @Autowired
    public VacationPayController(VacationPayService vacationPayService) {
        this.vacationPayService = vacationPayService;
    }

    @GetMapping("/calculate")
    public ResponseEntity<?> calculateVacationPay(@RequestParam(name = "averageSalary") BigDecimal averageSalary,
                                                  @RequestParam(name = "vacationDuration") Integer vacationDuration,
                                                  @RequestParam(name = "vacationStartDate", required = false)
                                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                  Optional<LocalDate> vacationStartDate) {
        return vacationStartDate
                .map(startDate -> new ResponseEntity<>(
                        vacationPayService.calculateVacationPay(averageSalary, vacationDuration, startDate),
                        OK))
                .orElseGet(() -> new ResponseEntity<>(
                        vacationPayService.calculateVacationPay(averageSalary, vacationDuration),
                        OK));
    }
}
