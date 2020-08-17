package com.petelko.university.controller;

import static com.petelko.university.controller.GlobalExceptionHandler.LINE_MASK;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.petelko.university.repository.exception.QueryNotExecuteException;

@TestInstance(Lifecycle.PER_CLASS)
class GlobalExceptionHandlerTest {


    private MockMvc mockMvc;

    @Mock
    private StudentController studentController;

    @BeforeAll
    public void setUpBeforeClass() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(studentController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void testQueryNotExecuteException() throws Exception {
        when(studentController.getAll()).thenThrow(QueryNotExecuteException.class);
        String msg = String.format(LINE_MASK, " with database");
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/students/getAll");
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("/global-error"))
                .andExpect(MockMvcResultMatchers.model().attribute("errorMessage", msg));
    }
}
