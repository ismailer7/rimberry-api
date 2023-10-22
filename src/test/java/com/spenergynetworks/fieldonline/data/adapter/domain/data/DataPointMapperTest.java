package com.spenergynetworks.fieldonline.data.adapter.domain.data;

import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.spenergynetworks.fieldonline.data.adapter.domain.data.influx.DataPointMapper;
import com.spenergynetworks.fieldonline.data.adapter.domain.data.influx.DataPointMapping;
import com.spenergynetworks.fieldonline.data.adapter.exception.DatapointNotFoundException;

class DataPointMapperTest {

    @Mock
    private Map<String, DataPointMapping> datapointsMap;

    private DataPointMapper dataPointMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        dataPointMapper = new DataPointMapper(datapointsMap);
    }

    @Test
    void testGetInfluxDbDatapointName() {
        // Create sample data
        var fieldOnlineDataPointName = "sampleFieldOnlineName";
        var influxDbDataPointName = "sampleInfluxDbName";
        var dataPointMapping = new DataPointMapping(influxDbDataPointName, fieldOnlineDataPointName, "", null);
        dataPointMapping.setInfluxDbName(influxDbDataPointName);

        // Mock the behaviour of the datapointsMap
        when(datapointsMap.get(fieldOnlineDataPointName)).thenReturn(dataPointMapping);

        // Call the getInfluxDbDatapointName method of DataPointMapper
        var result = dataPointMapper.getInfluxDbDatapointName(fieldOnlineDataPointName);

        // Assert the result
        Assertions.assertEquals(influxDbDataPointName, result);
    }

    @Test
    void testGetInfluxDbDatapointNameNotFound() {
        // Create sample data
        var fieldOnlineDataPointName = "nonExistingField";

        // Mock the behavior of the datapointsMap
        when(datapointsMap.get(fieldOnlineDataPointName)).thenReturn(null);

        // Call the getInfluxDbDatapointName method of DataPointMapper and assert that
        // it throws DatapointNotFoundException
        Assertions.assertThrows(DatapointNotFoundException.class, () -> {
            dataPointMapper.getInfluxDbDatapointName(fieldOnlineDataPointName);
        });
    }

    @Test
    void testGetDataPointType() {
        // Create sample data
        var fieldOnlineDataPointName = "sampleFieldOnlineName";
        var dataPointMapping = new DataPointMapping(fieldOnlineDataPointName, fieldOnlineDataPointName, "",
                DataPointMapping.Type.MEASUREMENT);

        // Mock the behaviour of the datapointsMap
        when(datapointsMap.get(fieldOnlineDataPointName)).thenReturn(dataPointMapping);

        // Call the getDataPointType method of DataPointMapper
        var result = dataPointMapper.getDataPointType(fieldOnlineDataPointName);

        // Assert the result
        Assertions.assertEquals(DataPointMapping.Type.MEASUREMENT, result);
    }

    @Test
    void testGetDataPointTypeNotFound() {
        // Create sample data
        var fieldOnlineDataPointName = "nonExistingField";

        // Mock the behavior of the datapointsMap
        when(datapointsMap.get(fieldOnlineDataPointName)).thenReturn(null);

        // Call the getDataPointType method of DataPointMapper and assert that it throws
        // DatapointNotFoundException
        Assertions.assertThrows(DatapointNotFoundException.class, () -> {
            dataPointMapper.getDataPointType(fieldOnlineDataPointName);
        });

    }

    void testGetDatapointMapping() {
        var fieldOnlineDataPointName = "sampleFieldOnlineName";
        var dataPointMapping = new DataPointMapping(fieldOnlineDataPointName, fieldOnlineDataPointName,
                DataPointMapping.Type.MEASUREMENT);

        // Mock the behaviour of the datapointsMap
        when(datapointsMap.get(fieldOnlineDataPointName)).thenReturn(dataPointMapping);
        when(datapointsMap.get("nonExistingField")).thenReturn(null);

        // Call the getDataPointType method of DataPointMapper
        var result = dataPointMapper.getDatapointMapping(fieldOnlineDataPointName);

        // Assert the result
        Assertions.assertEquals(dataPointMapping, result);

        Assertions.assertThrows(DatapointNotFoundException.class, () -> {
            dataPointMapper.getDatapointMapping("nonExistingField");
        });
    }
}