package com.spenergynetworks.fieldonline.data.adapter.domain.data;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.OffsetDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.spenergynetworks.fieldonline.data.adapter.domain.data.influx.DataPointMapping;
import com.spenergynetworks.fieldonline.data.adapter.domain.data.influx.DataPointMapping.Type;
import com.spenergynetworks.fieldonline.data.adapter.domain.data.influx.InfluxQueryHelper;
import com.spenergynetworks.fieldonline.data.adapter.model.QueryData;

class InfluxQueryHelperTest {

    @Test
    void buildDataQuery_ReturnsCorrectDataQuery() {
        // Arrange
        var queryData = new QueryData(); // Set up the necessary query data
        queryData.setStartTime(OffsetDateTime.parse("2023-05-30T12:00:00Z"));
        queryData.setEndTime(OffsetDateTime.parse("2023-06-02T12:00:00Z"));
        var datapoint = new DataPointMapping("myDataPoint", "", Type.MEASUREMENT);

        var bucket = "myBucket";
        var measurement = "myMeasurement";
        var expectedQuery = "from(bucket: \"myBucket\")"
                + "|>range(start:2023-05-30T12:00:00Z,stop:2023-06-02T12:00:00Z)"
                + "|>filter(fn: (r) => r[\"_measurement\"] == \"myMeasurement\" "
                + "and r[\"_field\"] == \"myDataPoint\")";

        // Act
        var dataQuery = InfluxQueryHelper.buildDataQuery(queryData, datapoint, bucket, measurement);

        // Assert
        assertThat(dataQuery).isEqualTo(expectedQuery);
    }

    @Test
    void buildDataQuery_withDeviceIDs_ReturnsCorrectDataQuery() {
        // Arrange
        var queryData = new QueryData(); // Set up the necessary query data
        queryData.setStartTime(OffsetDateTime.parse("2023-05-30T12:00:00Z"));
        queryData.setEndTime(OffsetDateTime.parse("2023-06-02T12:00:00Z"));
        queryData.setDeviceIds(List.of("1234","5678"));
        var datapoint = new DataPointMapping("myDataPoint", "", Type.MEASUREMENT);
        var bucket = "myBucket";
        var measurement = "myMeasurement";
        var expectedQuery = "from(bucket: \"myBucket\")"
                + "|>range(start:2023-05-30T12:00:00Z,stop:2023-06-02T12:00:00Z)"
                + "|>filter(fn: (r) => r[\"_measurement\"] == \"myMeasurement\" "
                + "and r[\"_field\"] == \"myDataPoint\")"
                +"|>filter(fn: (r) => r[\"device_id\"] =~ /^(1234|5678)$/)";

        // Act
        var dataQuery = InfluxQueryHelper.buildDataQuery(queryData, datapoint, bucket, measurement);

        // Assert
        assertThat(dataQuery).isEqualTo(expectedQuery);
    }

    @Test
    void buildAverageDataQuery_ReturnsCorrectAverageDataQuery() {
        // Arrange
        var queryData = new QueryData(); // Set up the necessary query data
        queryData.setStartTime(OffsetDateTime.parse("2023-05-30T12:00:00Z"));
        queryData.setEndTime(OffsetDateTime.parse("2023-06-02T12:00:00Z"));
        var datapoint = new DataPointMapping("myDataPoint", "", Type.MEASUREMENT);
        var bucket = "myBucket";
        var measurement = "myMeasurement";
        var expectedQuery = "from(bucket: \"myBucket\")"
                + "|>range(start:2023-05-30T12:00:00Z,stop:2023-06-02T12:00:00Z)"
                + "|>filter(fn: (r) => r[\"_measurement\"] == \"myMeasurement\" "
                + "and r[\"_field\"] == \"myDataPoint\")"
                + "|>window(every: 30m)"
                + "|>timeWeightedAvg(unit: 10m)";

        // Act
        var averageDataQuery = InfluxQueryHelper.buildAverageDataQuery(queryData, datapoint, bucket, measurement);

        // Assert
        assertThat(averageDataQuery).isEqualTo(expectedQuery);
    }

    @Test
    void buildEventQuery_ReturnsCorrectQuery() {
        var queryData = new QueryData(); // Set up the necessary query data
        queryData.setStartTime(OffsetDateTime.parse("2023-05-30T12:00:00Z"));
        queryData.setEndTime(OffsetDateTime.parse("2023-06-02T12:00:00Z"));
        var datapoint = new DataPointMapping("myDataPoint", "", Type.EVENT);
        var bucket = "myBucket";
        var measurement = "myMeasurement";
        var expectedQuery = "import \"influxdata/influxdb/schema\"from(bucket: \"myBucket\")|>range(start:2023-05-30T12:00:00Z,stop:2023-06-02T12:00:00Z)|>filter(fn: (r) => r[\"_measurement\"] == \"myMeasurement\")|> toString()|> schema.fieldsAsCols()|> filter(fn: (r)=> r[\"type\"]=~ /^(myDataPoint)$/)|> keep(columns: [\"event_id\",\"start_date\",\"line\",\"status\",\"type\",\"end_date\"])";

        var eventQuery = InfluxQueryHelper.buildDataQuery(queryData, datapoint, bucket, measurement);

        assertThat(eventQuery).isEqualTo(expectedQuery);

        queryData.setDeviceIds(List.of("1234","5678"));

        expectedQuery = "import \"influxdata/influxdb/schema\"from(bucket: \"myBucket\")|>range(start:2023-05-30T12:00:00Z,stop:2023-06-02T12:00:00Z)|>filter(fn: (r) => r[\"_measurement\"] == \"myMeasurement\")|>filter(fn: (r) => r[\"device_id\"] =~ /^(1234|5678)$/)|> toString()|> schema.fieldsAsCols()|> filter(fn: (r)=> r[\"type\"]=~ /^(myDataPoint)$/)|> keep(columns: [\"event_id\",\"start_date\",\"line\",\"status\",\"type\",\"end_date\"])";
        eventQuery = InfluxQueryHelper.buildDataQuery(queryData, datapoint, bucket, measurement);

        assertThat(eventQuery).isEqualTo(expectedQuery);
    }
}
