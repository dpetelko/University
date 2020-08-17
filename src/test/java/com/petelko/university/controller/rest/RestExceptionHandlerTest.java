package com.petelko.university.controller.rest;

import com.petelko.university.repository.exception.QueryNotExecuteException;
import com.petelko.university.service.StudentService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.petelko.university.controller.rest.RestExceptionHandler.LINE_MASK;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(Lifecycle.PER_CLASS)
class RestExceptionHandlerTest {

    private MockMvc mockMvc;

    @Mock
    private StudentService studentService;
    @InjectMocks
    private StudentRestController studentController;

    @BeforeAll
    public void setUpBeforeClass() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(studentController)
                .setControllerAdvice(new RestExceptionHandler())
                .build();
    }

    @Test
    void testQueryNotExecuteException() throws Exception {
        when(studentService.getAllDTO()).thenThrow(QueryNotExecuteException.class);
        String msg = String.format(LINE_MASK, " with database");
        mockMvc.perform(get("/api/students")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError())
                .andExpect(result -> is(msg));
    }

}
