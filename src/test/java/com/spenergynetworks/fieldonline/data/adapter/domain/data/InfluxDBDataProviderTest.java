    package com.spenergynetworks.fieldonline.data.adapter.domain.data;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.exceptions.BadRequestException;
import com.spenergynetworks.fieldonline.data.adapter.domain.data.influx.DataPointMapper;
import com.spenergynetworks.fieldonline.data.adapter.domain.data.influx.DataPointMapping;
import com.spenergynetworks.fieldonline.data.adapter.domain.data.influx.DataPointMapping.Type;
import com.spenergynetworks.fieldonline.data.adapter.domain.data.influx.InfluxDBDataProvider;
import com.spenergynetworks.fieldonline.data.adapter.domain.data.influx.InfluxDBProperties;
import com.spenergynetworks.fieldonline.data.adapter.domain.data.influx.InfluxMeasurementResponse;
import com.spenergynetworks.fieldonline.data.adapter.exception.DataBaseErrorException;
import com.spenergynetworks.fieldonline.data.adapter.model.QueryData;

@ExtendWith(MockitoExtension.class)
class InfluxDBDataProviderTest {

    @Mock
    private InfluxDBClient client;
    @Mock
    private QueryApi api;
    @Mock
    private DataPointMapper dataPointMapper;

    private InfluxDBProperties props;

    private static final String DATA_QUERY = "from(bucket: \"bucketName\")|>range(start:2023-05-30T12:00:00Z,stop:2023-06-02T12:00:00Z)|>filter(fn: (r) => r[\"_measurement\"] == \"measurement_name\" and r[\"_field\"] == \"influx_field\")";
    private static final String DATA_QUERY_THROW = "from(bucket: \"bucketName\")|>range(start:2023-05-30T12:00:00Z,stop:2023-06-02T12:00:00Z)|>filter(fn: (r) => r[\"_measurement\"] == \"measurement_name\" and r[\"_field\"] == \"throw\")";

    private static final String AVERAGED_DATA_QUERY = "from(bucket: \"bucketName\")|>range(start:2023-05-30T12:00:00Z,stop:2023-06-02T12:00:00Z)|>filter(fn: (r) => r[\"_measurement\"] == \"measurement_name\" and r[\"_field\"] == \"influx_field\")|>window(every: 30m)|>timeWeightedAvg(unit: 10m)";

    private static final String AVERAGED_DATA_QUERY_THROW = "from(bucket: \"bucketName\")|>range(start:2023-05-30T12:00:00Z,stop:2023-06-02T12:00:00Z)|>filter(fn: (r) => r[\"_measurement\"] == \"measurement_name\" and r[\"_field\"] == \"throw\")|>window(every: 30m)|>timeWeightedAvg(unit: 10m)";

    @Test
    void test() {

        props = new InfluxDBProperties("bucketName","eventsBucket", "org", "measurement_name","event_name");

        var goodRes = new InfluxMeasurementResponse();
        goodRes.setField("influx_field");
        goodRes.setValue(1d);
        goodRes.setTime(Instant.now());
        var badRes = new InfluxMeasurementResponse();
        badRes.setField("influx_field");
        badRes.setValue(2d);
        badRes.setTime(Instant.now());

        when(client.getQueryApi()).thenReturn(api);

        // "default" return value
        when(api.query(anyString(), anyString(), eq(InfluxMeasurementResponse.class))).thenReturn(List.of(badRes));
        // specific returns
        when(api.query(DATA_QUERY, "org", InfluxMeasurementResponse.class)).thenReturn(List.of(goodRes));
        when(api.query(AVERAGED_DATA_QUERY, "org", InfluxMeasurementResponse.class)).thenReturn(List.of(goodRes));
        when(api.query(DATA_QUERY_THROW, "org", InfluxMeasurementResponse.class)).thenThrow(new BadRequestException(null));

        var datapoint1 = new DataPointMapping("influx_field", "testPoint", Type.MEASUREMENT);
        var datapoint2 = new DataPointMapping("throw", "throwTestPoint", Type.MEASUREMENT);

        when(dataPointMapper.getDatapointMapping("testPoint")).thenReturn(new DataPointMapping("influx_field", "testPoint", "Multiply by 10^3", DataPointMapping.Type.MEASUREMENT));
        when(dataPointMapper.getDatapointMapping("testPoint")).thenReturn(datapoint1);
        when(dataPointMapper.getDatapointMapping("throwTestPoint")).thenReturn(datapoint2);
        var queryData = new QueryData();

        queryData.setStartTime(OffsetDateTime.parse("2023-05-30T12:00:00Z"));
        queryData.setEndTime(OffsetDateTime.parse("2023-06-02T12:00:00Z"));

        var dataPoint = "testPoint";

        var dataProvider = new InfluxDBDataProvider(client, props, dataPointMapper);

        assertThat(dataProvider.getData(queryData, dataPoint).get(0).getDataPointName()).isEqualTo("testPoint");
        assertThat(dataProvider.getData(queryData, dataPoint).get(0).getValue()).isEqualTo("1.0");
        assertThat(dataProvider.getAveragedData(queryData, dataPoint, "30m", "10m").get(0).getDataPointName())
                .isEqualTo("testPoint");

        assertThrows(DataBaseErrorException.class, () -> dataProvider.getData(queryData, "throwTestPoint"));

    }

}
