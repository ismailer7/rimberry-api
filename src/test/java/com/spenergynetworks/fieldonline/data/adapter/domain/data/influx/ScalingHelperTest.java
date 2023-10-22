package com.spenergynetworks.fieldonline.data.adapter.domain.data.influx;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.spenergynetworks.fieldonline.data.adapter.exception.DataConverterException;

class ScalingHelperTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void givenValidMultiplyByRuleShouldTransformData() {
        var value = "1.0";
        var rule1 = "multiply by 10^3";
        var rule2 = "multiply by 1000";

        assertThat(ScalingHelper.transformData(rule1, value)).isEqualTo("1000.0");
        assertThat(ScalingHelper.transformData(rule2, value)).isEqualTo("1000.0");
    }

    @Test
    void givenMultiplyByRuleWithNoValueShouldThrowException() {
        var value = "1000";
        var rule = "multiply by ";

        assertThatThrownBy(() -> ScalingHelper.transformData(rule, value)).isInstanceOf(DataConverterException.class)
                .hasMessage("Business rule 'multiply by' with no specified value");
    }

    @Test
    void givenInvalidValueShouldThrowException() {
        var value = "aa";
        var rule = "multiply by 10^3 ";

        assertThatThrownBy(() -> ScalingHelper.transformData(rule, value)).isInstanceOf(DataConverterException.class)
                .hasMessageContaining("is not a valid number");
    }

    @Test
    void givenUnsupportedRuleShouldThrowException() {
        var value = "1000";
        var rule = "divide by 10^3 ";

        assertThatThrownBy(() -> ScalingHelper.transformData(rule, value)).isInstanceOf(DataConverterException.class)
                .hasMessageContaining("Business rule not supported");
    }


    @Test
    void testWithEmptyOrNullBusinessRule() {
        var value = "1000";
        var rule = "";
        assertThat(ScalingHelper.transformData(rule, value)).isEqualTo(value);
        assertThat(ScalingHelper.transformData(null, value)).isEqualTo(value);
    }

}