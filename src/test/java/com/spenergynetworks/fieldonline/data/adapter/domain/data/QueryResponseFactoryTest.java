package com.spenergynetworks.fieldonline.data.adapter.domain.data;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.spenergynetworks.fieldonline.data.adapter.domain.data.influx.DataPointMapping;
import com.spenergynetworks.fieldonline.data.adapter.domain.data.influx.InfluxEventResponse;
import com.spenergynetworks.fieldonline.data.adapter.domain.data.influx.InfluxMeasurementResponse;
import com.spenergynetworks.fieldonline.data.adapter.domain.data.influx.InfluxResponse;
import com.spenergynetworks.fieldonline.data.adapter.exception.DataConverterException;
import com.spenergynetworks.fieldonline.data.adapter.model.QueryResponse;

class QueryResponseFactoryTest {

    List<InfluxResponse> responses;
    DataPointMapping dataPointMapping1;
    DataPointMapping dataPointMapping2;

    DataPointMapping dataPointMapping3;

    @BeforeEach
    void setUp() {

        responses = new ArrayList<>();

        var response1 = new InfluxMeasurementResponse();
        response1.setField("field");
        response1.setFuseId("fuseId");
        response1.setSubstationEnid("substationEnid");
        response1.setLicenceArea("licenceArea");
        response1.setTransformerEnid("transformerEnid");
        response1.setPhase("phase");
        response1.setValue(42d);
        response1.setTime(Instant.parse("2023-06-19T12:00:00Z"));

        var response2 = new InfluxMeasurementResponse();
        response2.setField("field");
        response2.setFuseId("fuseId");
        response2.setSubstationEnid("substationEnid");
        response2.setLicenceArea("licenceArea");
        response2.setTransformerEnid("transformerEnid");
        response2.setPhase("phase");
        response2.setValue(42d);
        response2.setStart(Instant.parse("2023-06-19T12:00:00Z"));

        responses.add(response1);
        responses.add(response2);

        dataPointMapping1 = new DataPointMapping("influx_field", "field", "Multiply by 1000",
                DataPointMapping.Type.MEASUREMENT);
        dataPointMapping2 = new DataPointMapping("influx_field", "field", "Multiply by 10^3",
                DataPointMapping.Type.MEASUREMENT);
        dataPointMapping3 = new DataPointMapping("influx_field", "field", "Multiply by ",
                DataPointMapping.Type.MEASUREMENT);
    }

    @Test
    void testGetQueryResponse_withNonNullTime_andValidDivideBy() {
        // Create a sample InfluxResponse with non-null time
        var response = new InfluxMeasurementResponse();

        // Get the QueryResponse using the QueryResponseFactory
        var queryResponses = QueryResponseFactory.getQueryResponse(responses, dataPointMapping1);

        Assertions.assertNotNull(queryResponses);

        for (QueryResponse queryResponse : queryResponses) {
            Assertions.assertEquals("field", queryResponse.getDataPointName());
            Assertions.assertEquals("fuseId", queryResponse.getFuseId());
            Assertions.assertEquals("substationEnid", queryResponse.getSubstationEnid());
            Assertions.assertEquals("licenceArea", queryResponse.getLicenceArea());
            Assertions.assertEquals("transformerEnid", queryResponse.getTransformerEnid());
            Assertions.assertEquals("400",queryResponse.getVoltage());
            Assertions.assertEquals("42000.0", queryResponse.getValue());
            Assertions.assertEquals("phase", queryResponse.getPhase());
            Assertions.assertEquals(OffsetDateTime.parse("2023-06-19T12:00:00Z"), queryResponse.getTimestamp());
        }
    }

    @Test
    void testGetQueryResponse_withNonNullTime_andValidDivideByExp() {

        var queryResponses = QueryResponseFactory.getQueryResponse(responses, dataPointMapping2);
        Assertions.assertNotNull(queryResponses);

        for (QueryResponse queryResponse : queryResponses) {
            Assertions.assertEquals("field", queryResponse.getDataPointName());
            Assertions.assertEquals("fuseId", queryResponse.getFuseId());
            Assertions.assertEquals("substationEnid", queryResponse.getSubstationEnid());
            Assertions.assertEquals("licenceArea", queryResponse.getLicenceArea());
            Assertions.assertEquals("transformerEnid", queryResponse.getTransformerEnid());
            Assertions.assertEquals("400",queryResponse.getVoltage());
            Assertions.assertEquals("42000.0", queryResponse.getValue());
            Assertions.assertEquals("phase", queryResponse.getPhase());
            Assertions.assertEquals(OffsetDateTime.parse("2023-06-19T12:00:00Z"), queryResponse.getTimestamp());
        }
    }

    @Test
    void testGetQueryResponse_withNonNullTime_andNoValueDivideBy() {
        assertThatThrownBy(() -> QueryResponseFactory.getQueryResponse(responses, dataPointMapping3))
                .isInstanceOf(DataConverterException.class)
                .hasMessage("Business rule 'multiply by' with no specified value");
    }

    @Test
    void testGetQueryResponse_WithNullResponses() {
        var queryResponses = QueryResponseFactory.getQueryResponse(null, dataPointMapping3);
        Assertions.assertNotNull(queryResponses);
        Assertions.assertEquals(0, queryResponses.size());
    }

    @Test
    void testGetQueryResponse_WithEmptyResponses() {
        var queryResponses = QueryResponseFactory.getQueryResponse(new ArrayList<>(), dataPointMapping3);
        Assertions.assertNotNull(queryResponses);
        Assertions.assertEquals(0, queryResponses.size());
    }

    @Test
    void testGetQueryResponse_event() {
        // Create a sample InfluxResponse with non-null time
        var response = new InfluxEventResponse();
        response.setFuseId("fuseId");
        response.setSubstationEnid("substationEnid");
        response.setLicenceArea("licenceArea");
        response.setTransformerEnid("transformerEnid");
        response.setPhase("phase");
        response.setStatus("ON");
        response.setStartDate("04/10/2023 15:00:31");
        var dataPointMapping = new DataPointMapping("influx_field", "field",
                DataPointMapping.Type.EVENT);

        // Get the QueryResponse using the QueryResponseFactory
        var queryResponse = QueryResponseFactory.getQueryResponse(List.of(response), dataPointMapping).get(0);

        // Assert the values in the QueryResponse
        Assertions.assertEquals("fuseId", queryResponse.getFuseId());
        Assertions.assertEquals("substationEnid", queryResponse.getSubstationEnid());
        Assertions.assertEquals("licenceArea", queryResponse.getLicenceArea());
        Assertions.assertEquals("transformerEnid", queryResponse.getTransformerEnid());
        Assertions.assertEquals("400",queryResponse.getVoltage());
        Assertions.assertEquals("ON", queryResponse.getValue());
        Assertions.assertEquals("phase", queryResponse.getPhase());

        response.setEndDate("04/10/2023 15:10:31");
        queryResponse = QueryResponseFactory.getQueryResponse(List.of(response), dataPointMapping).get(0);
        Assertions.assertEquals(OffsetDateTime.parse("2023-04-10T15:10:31Z"), queryResponse.getTimestamp());
    }

}
