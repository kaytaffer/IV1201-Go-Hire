package kth.iv1201.gohire.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import kth.iv1201.gohire.DTO.CreateApplicantRequestDTO;
import kth.iv1201.gohire.DTO.LoginRequestDTO;
import kth.iv1201.gohire.controller.PersonController;
import kth.iv1201.gohire.service.PersonService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class SecurityConfigurationTest {

    @MockBean
    PersonController personController;
    @MockBean
    PersonService personService;

    LoginRequestDTO mockLoginRequestDTO;
    CreateApplicantRequestDTO mockCreateApplicantRequestDTO;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockLoginRequestDTO = new LoginRequestDTO("exampleUsername", "examplePassword");
        mockCreateApplicantRequestDTO = new CreateApplicantRequestDTO("exampleFirstName", "exampleLastName", "example@example.com", "00123456-7890", "exampleUsername", "examplePassword");
    }

    @AfterEach
    void tearDown() {
        mockLoginRequestDTO = null;
        mockCreateApplicantRequestDTO = null;
    }

    @Test
    @WithAnonymousUser
    void testIfNotLoggedInUserCanAccessLogin() {
        assertDoesNotThrow(() -> {
            mockMvc.perform(post("/api/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(mockLoginRequestDTO)))
                    .andExpect(status().isOk());
        }, "Non-logged in user could not access login.");
    }

    @Test
    @WithAnonymousUser
    void testIfNotLoggedInUserCanAccessCreateApplicant() {
        assertDoesNotThrow(() -> {
            mockMvc.perform(post("/api/createApplicant")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(mockCreateApplicantRequestDTO)))
                    .andExpect(status().isOk());
        }, "Non-logged in user could not access create applicant.");
    }

    @Test
    @WithAnonymousUser
    void testIfNotLoggedInUserCanAccessHtml() {
        assertDoesNotThrow(() -> {
            mockMvc.perform(get("/")).andExpect(status().isOk());
        }, "Non-logged in user could not access HTML.");
    }

}