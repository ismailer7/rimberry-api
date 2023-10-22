package com.spenergynetworks.fieldonline.data.adapter.domain.data;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.spenergynetworks.fieldonline.data.adapter.exception.DataBaseErrorException;
import com.spenergynetworks.fieldonline.data.adapter.exception.DatapointNotFoundException;
import com.spenergynetworks.fieldonline.data.adapter.model.QueryResponse;
import com.spenergynetworks.fieldonline.data.adapter.model.QueryData;

@TestConfiguration
public class IntegrationTestContextConfiguration {

    @Bean
    public DataProvider dataProvider() {
        return new DataProvider() {

            @Override
            public List<QueryResponse> getData(QueryData interval, String datapoint) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public List<QueryResponse> getAveragedData(QueryData interval, String datapoint, String window,
                    String step) {

                if (datapoint.equals("dbError")) {
                    throw new DataBaseErrorException("");
                }

                if (datapoint.equals("notfound")) {
                    throw new DatapointNotFoundException(datapoint);
                }

                List<QueryResponse> responses = List.of(
                        new QueryResponse().value("10.0").timestamp(OffsetDateTime.parse("2023-02-04T11:50:00Z"))
                                .dataPointName("ReactivePower1").licenceArea("SPM").phase("L1")
                                .substationEnid("10238995")
                                .voltage("400").fuseId("1"),
                        new QueryResponse().value("5.0").timestamp(OffsetDateTime.parse("2023-02-04T11:50:00Z"))
                                .dataPointName("ReactivePower2").licenceArea("SPM").phase("L1")
                                .substationEnid("10235695")
                                .voltage("400").fuseId("1"),
                        new QueryResponse().value("10.0").timestamp(OffsetDateTime.parse("2023-02-04T11:50:00Z"))
                                .dataPointName("ReactivePower3").licenceArea("SPM").phase("L1")
                                .substationEnid("10233295")
                                .voltage("400").fuseId("1"),
                        new QueryResponse().value("22.5").timestamp(OffsetDateTime.parse("2023-02-04T11:50:00Z"))
                                .dataPointName("ReactivePower4").licenceArea("SPM").phase("L1")
                                .substationEnid("10231295")
                                .voltage("400").fuseId("1"),
                        new QueryResponse().value("14.3").timestamp(OffsetDateTime.parse("2023-02-04T11:50:00Z"))
                                .dataPointName("ReactivePower5").licenceArea("SPM").phase("L1")
                                .substationEnid("10231195")
                                .voltage("400").fuseId("1"),
                        new QueryResponse().value("-5.2").timestamp(OffsetDateTime.parse("2023-02-04T11:50:00Z"))
                                .dataPointName("ReactivePower6").licenceArea("SPM").phase("L1")
                                .substationEnid("10235595")
                                .voltage("400").fuseId("1"));

                return responses;
            }
        };
    }

}
