package kuprianowicz.michal.DynatraceTask.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kuprianowicz.michal.DynatraceTask.exchange.controller.ExchangeController;
import kuprianowicz.michal.DynatraceTask.exchange.service.ExchangeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(ExchangeController.class)
class ExchangeControllerIntTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ExchangeService exchangeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getExchangeByCodeAndDate_ReturnValue() throws Exception {
        /*given(exchangeService.getExchangeByCodeAndDate(ArgumentMatchers.any(),ArgumentMatchers.any())).willAnswer(invocation -> invocation.getArgument(0));

        RequestBuilder request = MockMvcRequestBuilders.get("/api/exchanges/gbp/2023-01-02").contentType(MediaType.APPLICATION_JSON);

        //ResultActions response = mvc.perform(request).andExpect((MockMvcResultMatchers.status.()));


        MvcResult result = mvc.perform(request).andReturn();

        assertEquals("5.2768",result.getResponse().getContentAsString());*/
    }

    @Test
    void getExchangeByCodeWithQuotationsReturnMid() {
    }

    @Test
    void getExchangeByCodeWithQuotationsReturnBuyAsk() {
    }
}