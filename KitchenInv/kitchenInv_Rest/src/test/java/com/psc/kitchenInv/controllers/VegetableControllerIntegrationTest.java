package com.psc.kitchenInv.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.psc.kitchenInv.Fruit;
import com.psc.kitchenInv.TestDataUtil;
import com.psc.kitchenInv.Vegetable;
import com.psc.kitchenInv.dto.FruitDTO;
import com.psc.kitchenInv.dto.VegetableDTO;
import com.psc.kitchenInv.services.VegetableService;
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
public class VegetableControllerIntegrationTest {

    private VegetableService vegetableService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    public VegetableControllerIntegrationTest(MockMvc mockMvc, VegetableService vegetableService) {
        this.mockMvc = mockMvc;
        this.vegetableService = vegetableService;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreateVegetableSuccessfullyReturnsHttp201Created() throws Exception {
        Vegetable v = TestDataUtil.buildVegA();
        String s = objectMapper.writeValueAsString(v);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/vegetables")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(s)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateVegetableReturnsSavedVegetable() throws Exception {
        Vegetable v = TestDataUtil.buildVegA();
        String s = objectMapper.writeValueAsString(v);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/vegetables")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(s)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(1L)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("onion")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.vegClass").value("ALLIUM")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(0)
        );
    }

    @Test
    public void testThatListVegetablesReturnsHttp200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/vegetables")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatListVegetablesListsCorrectVegetables() throws Exception {
        Vegetable v = TestDataUtil.buildVegA();
        vegetableService.save(v);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/vegetables")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").value(1L)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value("onion")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].vegClass").value("ALLIUM")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].age").value(0)
        );
    }

    @Test
    public void testThatListVegetableReturnsHttpStatus200WhenVegetableExists() throws Exception {
        Vegetable v = TestDataUtil.buildVegA();
        vegetableService.save(v);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/vegetables/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatListVegetableReturnsHttpStatus404WhenVegetableDoesNotExists() throws Exception {
        Vegetable v = TestDataUtil.buildVegA();
        vegetableService.save(v);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/vegetables/99")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatGetVegetablesReturnsCorrectVegetable() throws Exception {
        Vegetable v = TestDataUtil.buildVegA();
        vegetableService.save(v);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/vegetables/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(1L)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("onion")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.vegClass").value("ALLIUM")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(0)
        );
    }

    @Test
    public void testThatFullUpdateVegetableReturnsHttpStatus404WhenNoVegetableExists() throws Exception {
        VegetableDTO vDTO = TestDataUtil.buildVegetableDTO();
        String vDTOJSON = objectMapper.writeValueAsString(vDTO);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/vegetables/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(vDTOJSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatFullUpdateVegetableReturnsHttpStatus200WhenVegetableExists() throws Exception {
        Vegetable v = TestDataUtil.buildVegA();
        Vegetable saved = vegetableService.save(v);

        VegetableDTO vDTO = TestDataUtil.buildVegetableDTO();
        String vDTOJSON = objectMapper.writeValueAsString(vDTO);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/vegetables/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(vDTOJSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatFullUpdateVegetableActuallyFullUpdatesExistingVegetable() throws Exception {
        Vegetable v = TestDataUtil.buildVegA();
        Vegetable saved = vegetableService.save(v);

        VegetableDTO vDTO = TestDataUtil.buildVegetableDTO();
        String vDTOJSON = objectMapper.writeValueAsString(vDTO);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/vegetables/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(vDTOJSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(1L)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("celery")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.vegClass").value("EDIBLE_STEM")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(0)
        );
    }

    @Test
    public void testThatPartialUpdateVegetableReturnsHttpStatus404WhenNoVegetableExists() throws Exception {
        VegetableDTO vDTO = VegetableDTO.builder().name("partial").build();
        String vDTOJSON = objectMapper.writeValueAsString(vDTO);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/vegetables/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(vDTOJSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatPartialUpdateVegetableReturnsHttpStatus200WhenVegetableExists() throws Exception {
        Vegetable v = TestDataUtil.buildVegA();
        Vegetable saved = vegetableService.save(v);

        VegetableDTO vDTO = VegetableDTO.builder().name("partial").build();
        String vDTOJSON = objectMapper.writeValueAsString(vDTO);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/vegetables/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(vDTOJSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatPartialUpdateVegetableActuallyPartialUpdatesExistingVegetable() throws Exception {
        Vegetable v = TestDataUtil.buildVegA();
        Vegetable saved = vegetableService.save(v);

        VegetableDTO vDTO = VegetableDTO.builder().name("partial").build();
        String vDTOJSON = objectMapper.writeValueAsString(vDTO);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/vegetables/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(vDTOJSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(1L)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("partial")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.vegClass").value("ALLIUM")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(0)
        );
    }

    @Test
    public void TestThatDeleteVegetableReturnsHttpStatus204() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/vegetables/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());

    }

    @Test
    public void TestThatDeleteVegetableReturnsHttpStatus204OnExistingAuthor() throws Exception {
        Vegetable v = TestDataUtil.buildVegA();
        Vegetable saved = vegetableService.save(v);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/vegetables/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
