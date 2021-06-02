package com.company.equipmentrecords.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TimeGraphObject extends GraphObject<BigDecimal, LocalDate> {

    private BigDecimal additionalValue;

    public TimeGraphObject(BigDecimal value,LocalDate category,BigDecimal additionalValue) {
        super(value, category);
        this.additionalValue = additionalValue;
    }

    public BigDecimal getAdditionalValue() {
        return additionalValue;
    }

    public void setAdditionalValue(BigDecimal additionalValue) {
        this.additionalValue = additionalValue;
    }
}
