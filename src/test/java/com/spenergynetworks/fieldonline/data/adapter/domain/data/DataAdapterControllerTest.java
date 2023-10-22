package com.spenergynetworks.fieldonline.data.adapter.domain.data;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Import(IntegrationTestContextConfiguration.class)
class DataAdapterControllerTest {

    private String validBody = "{"
            + "\"StartTime\": \"2023-02-03T11:50:00Z\","
            + "\"EndTime\": \"2023-02-04T11:50:00Z\""
            + "}";

    private String validEndpoint = "/api/v1/test/30min";
    private String notfoundEndpoint = "/api/v1/notfound/30min";
    private String databaseErrorEndpoint = "/api/v1/dbError/30min";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(value = "admin")
    void dataPostWithBadRequestBody_shouldReturnBadRequest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post(validEndpoint)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .header("api-key ", "UeTcLveRpHDQfyXO3I5SBWCQc4HTWGa39Sx1Taig0scw6TXVKurXWI3gKzAJSPq2")
                .content("invalid data"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(value = "admin")
    void dataPostWithValidBodyAndDataPoint_shouldReturnOk() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post(validEndpoint)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.validBody))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "admin")
    void dataPostWithValidBodyAndInvalidDataPoint_shouldReturnDatapointNotFound() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post(notfoundEndpoint)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.validBody))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Datapoint not found")));
    }

    @Test
    @WithMockUser(value = "admin")
    void dataPostWithValidBodyAndDataPoint_databaseError_shouldReturnDatabaseErrorException() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post(databaseErrorEndpoint)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.validBody))
                .andDo(print())
                .andExpect(status().isBadGateway())
                .andExpect(jsonPath("$.message", is("Database error")));
    }

}