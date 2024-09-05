package com.psc.kitchenInv.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.psc.kitchenInv.Meat;
import com.psc.kitchenInv.TestDataUtil;
import com.psc.kitchenInv.mappers.impl.MeatMapperImpl;
import com.psc.kitchenInv.services.impl.MeatServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class MeatControllerIntegrationTest {

    private MeatServiceImpl meatService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    public MeatControllerIntegrationTest(MockMvc mockMvc, MeatServiceImpl meatService) {
        this.mockMvc = mockMvc;
        this.meatService = meatService;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void TestThatCreateMeatSendsHttp201Created() throws Exception {
        Meat m = TestDataUtil.buildMeatA();
        String s = objectMapper.writeValueAsString(m);
        mockMvc.perform(MockMvcRequestBuilders.post("/meat")
                .contentType(MediaType.APPLICATION_JSON)
                .content(s)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void TestThatMeatCanBeCreatedAndRecalled() throws Exception {
        Meat m = TestDataUtil.buildMeatA();
        String s = objectMapper.writeValueAsString(m);
        mockMvc.perform(MockMvcRequestBuilders.post("/meat")
                .contentType(MediaType.APPLICATION_JSON)
                .content(s)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(1L)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("ground beef")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.meatClass").value("BEEF")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.meatType").value("GROUND")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(0)
        );
    }

    @Test
    public void TestThatListMeatSendsHttp200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/meat")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void TestThatManyMeatCanBeCreatedAndRecalled() throws Exception {
        Meat m = TestDataUtil.buildMeatA();
        String s = objectMapper.writeValueAsString(m);
        mockMvc.perform(MockMvcRequestBuilders.get("/meat")
                .contentType(MediaType.APPLICATION_JSON)
                .content(s)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").value(1L)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value("ground beef")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].meatClass").value("BEEF")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].meatType").value("GROUND")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].age").value(0)
        );

    }

    @Test
    public void TestThatListMeatReturnsHttp200WhenExists() throws Exception {
        Meat m = TestDataUtil.buildMeatA();
        Meat saved = meatService.save(m);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/meat/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void TestThatListMeatReturnsHttp204WhenNotExists() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/meat/99")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void TestThatMeatCanBePartialUpdated() throws Exception {
        Meat m = TestDataUtil.buildMeatA();
        Meat saved = meatService.save(m);

        saved.setName("updated");
        Meat newMeat = meatService.save(saved);
        String s = objectMapper.writeValueAsString(saved);
        mockMvc.perform(MockMvcRequestBuilders.get("/meat/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(s)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(1L)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("updated")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.meatClass").value("BEEF")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.meatType").value("GROUND")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(0)
        );
    }

    @Test
    public void TestThatMeatCanBeFullUpdated() throws Exception {
        Meat m = TestDataUtil.buildMeatA();
        Meat saved = meatService.save(m);

        Meat a = TestDataUtil.buildMeatB();
        a.setId(saved.getId());
        String s = objectMapper.writeValueAsString(a);

        mockMvc.perform(MockMvcRequestBuilders.put("/meat/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(s)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(1L)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("pork shoulder")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.meatClass").value("PORK")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.meatType").value("CUT")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(0)
        );
    }

    @Test
    public void TestThatFullUpdateMeatReturnsHttp404WhenNoMeatExists() throws Exception {
        Meat m = TestDataUtil.buildMeatA();
        String s = objectMapper.writeValueAsString(m);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/meat/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(s)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void TestThatFullUpdateMeatReturnsHttp200WhenExists() throws Exception {
        Meat m = TestDataUtil.buildMeatA();
        String s = objectMapper.writeValueAsString(m);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/meat/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(s)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void TestThatDeleteMeatReturnsNoContent() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/meat/99")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void TestThatDeleteMeatReturnsNoContentWhenMeatExists() throws Exception {
        Meat m = TestDataUtil.buildMeatA();
        Meat saved = meatService.save(m);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/meat/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }










}
