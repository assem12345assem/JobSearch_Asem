package com.example.jobsearch.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

import java.time.LocalDate;

public class DatesComparisonValidator implements ConstraintValidator<DatesComparison, Object> {

    private String startDateFieldName;
    private String endDateFieldName;

    @Override
    public void initialize(DatesComparison constraintAnnotation) {
        this.startDateFieldName = constraintAnnotation.startDate();
        this.endDateFieldName = constraintAnnotation.endDate();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        LocalDate startDate = (LocalDate) new BeanWrapperImpl(value).getPropertyValue(startDateFieldName);
        LocalDate endDate = (LocalDate) new BeanWrapperImpl(value).getPropertyValue(endDateFieldName);

        if (startDate == null || endDate == null) {
            return true;
        }

        return startDate.isBefore(endDate);
    }
}
