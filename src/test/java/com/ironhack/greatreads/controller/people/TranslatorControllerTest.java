package com.ironhack.greatreads.controller.people;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.greatreads.model.people.Translator;
import com.ironhack.greatreads.repository.people.TranslatorRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class TranslatorControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private TranslatorRepository translatorRepository;
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private Translator translator1;
    private Translator translator2;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
        translator1 = new Translator("Marta", "Pera Cucurell");
        translator2 = new Translator("Jordi", "Martín Lloret");
        translatorRepository.saveAll(List.of(translator1, translator2));
    }

    @AfterEach
    void tearDown() {
        translatorRepository.deleteAll();
    }

    @Test
    void getAllTranslatorsTest() throws Exception {
        MvcResult result = mockMvc.perform(get("/translators"))
                .andExpect(status().isOk())
                .andReturn();

        List<Translator> translators = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Translator>>() {});
        assertEquals(2, translators.size());
        assertEquals(translator1.getFirstName(), translators.get(0).getFirstName());
        assertEquals(translator2.getFirstName(), translators.get(1).getFirstName());
    }

    @Test
    void getTranslatorTest() throws Exception {
        MvcResult result = mockMvc.perform(get("/translators/{id}", translator1.getId()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Translator translator = objectMapper.readValue(
                result.getResponse().getContentAsString(), Translator.class
        );

        assertEquals(translator1.getFirstName(), translator.getFirstName());
        assertEquals(translator1.getLastName(), translator.getLastName());
    }

    @Test
    void addTranslatorTest() throws Exception {
        Translator fixture = new Translator("Albert", "Nolla Cabellos");
        String body = objectMapper.writeValueAsString(fixture);

        MvcResult result = mockMvc.perform(post("/translators").with(user("test").roles("LIBRARIAN"))
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        Translator translator = objectMapper.readValue(result.getResponse().getContentAsString(), Translator.class);
        assertEquals("Albert", translator.getFirstName());
        assertEquals("Nolla Cabellos", translator.getLastName());
    }

    @Test
    void updateTranslatorTest() throws Exception {
        Translator updatedTranslator = new Translator(null, "Pera");

        String updatedTranslatorJson = objectMapper.writeValueAsString(updatedTranslator);

        MvcResult result = mockMvc.perform(patch("/translators/{id}", translator1.getId()).with(user("test").roles("LIBRARIAN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedTranslatorJson))
                .andExpect(status().isOk())
                .andReturn();

        Translator translator = objectMapper.readValue(
                result.getResponse().getContentAsString(), Translator.class
        );

        assertEquals(translator1.getId(), translator.getId());
        assertEquals(updatedTranslator.getLastName(), translator.getLastName());
    }

    @Test
    void deleteTranslatorTest() throws Exception {
        mockMvc.perform(delete("/translators/{id}", translator1.getId()).with(user("test").roles("ADMIN")))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/translators/{id}", translator1.getId()))
                .andExpect(status().isNotFound());
    }
}
