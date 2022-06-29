package net.thumbtack.school.buscompany.controller;

import net.thumbtack.school.buscompany.service.DebugService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ActiveProfiles(profiles = {"dev"})
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = DebugController.class)
class TestDebugController {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private DebugService debugService;

    @Test
    void testClear() throws Exception {
        MvcResult result = mvc.perform(post("/api/debug/clear")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

}