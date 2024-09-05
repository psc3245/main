package com.psc.kitchenInv.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.psc.kitchenInv.Fruit;
import com.psc.kitchenInv.TestDataUtil;
import com.psc.kitchenInv.dto.FruitDTO;
import com.psc.kitchenInv.services.FruitService;
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
public class FruitControllerIntegrationTest {

    private FruitService fruitService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    public FruitControllerIntegrationTest(MockMvc mockMvc, FruitService fruitService) {
        this.mockMvc = mockMvc;
        this.fruitService = fruitService;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreateFruitSuccessfullyReturnsHttp201Created() throws Exception {
        Fruit f = TestDataUtil.buildFruitA();
        String s = objectMapper.writeValueAsString(f);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/fruit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(s)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateFruitSuccessfullyReturnsSavedFruit() throws Exception {
        Fruit f = TestDataUtil.buildFruitA();
        String s = objectMapper.writeValueAsString(f);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/fruit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(s)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(1L)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("apple")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.fruitClass").value("APPLE_PEAR")
        ).andExpect(
                        MockMvcResultMatchers.jsonPath("$.age").value(0)
        );
    }

    @Test
    public void testThatListFruitReturnsHttpStatus200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/fruit")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    // Only works when pagination is DISABLED
    @Test
    public void testThatListFruitReturnsListOfFruit() throws Exception {
        Fruit f = TestDataUtil.buildFruitA();
        fruitService.save(f);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/fruit")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").value(1L)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value("apple")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].fruitClass").value("APPLE_PEAR")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].age").value(0)
        );
    }

    @Test
    public void testThatListFruitReturnsHttpStatus200WhenFruitExists() throws Exception {
        Fruit f = TestDataUtil.buildFruitA();
        fruitService.save(f);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/fruit/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatListFruitReturnsHttpStatus404WhenNoFruitExists() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/fruit/99")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatFindOneFruitReturnsCorrectFruit() throws Exception {
        Fruit f = TestDataUtil.buildFruitA();
        fruitService.save(f);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/fruit/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(1L)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("apple")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.fruitClass").value("APPLE_PEAR")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(0)
        );
    }

    @Test
    public void testThatFullUpdateFruitReturnsHttpStatus404WhenNoFruitExists() throws Exception {
        FruitDTO fDTO = TestDataUtil.buildFruitDTO();
        String fDTOJSON = objectMapper.writeValueAsString(fDTO);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/fruit/00")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(fDTOJSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatFullUpdateFruitReturnsHttpStatus200WhenFruitExists() throws Exception {
        Fruit f = TestDataUtil.buildFruitA();
        Fruit saved = fruitService.save(f);

        FruitDTO fDTO = TestDataUtil.buildFruitDTO();
        String fDTOJSON = objectMapper.writeValueAsString(fDTO);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/fruit/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(fDTOJSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatFullUpdateActuallyFullUpdatesExistingFruit() throws Exception {
        Fruit f = TestDataUtil.buildFruitA();
        Fruit saved = fruitService.save(f);

        FruitDTO fDTO = TestDataUtil.buildFruitDTO();
        fDTO.setId(saved.getId());

        String fDTOJSON = objectMapper.writeValueAsString(fDTO);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/fruit/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(fDTOJSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(1L)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("watermelon")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.fruitClass").value("MELON")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(0)
        );
    }

    @Test
    public void testThatPartialUpdateFruitReturnsHttpStatus200WhenFruitExists() throws Exception {
        Fruit f = TestDataUtil.buildFruitA();
        Fruit saved = fruitService.save(f);

        FruitDTO fDTO = TestDataUtil.buildFruitDTO();
        String fDTOJSON = objectMapper.writeValueAsString(fDTO);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/fruit/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(fDTOJSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatPartialUpdateFruitReturnsUpdatedFruit() throws Exception {
        Fruit f = TestDataUtil.buildFruitA();
        Fruit saved = fruitService.save(f);

        FruitDTO fDTO = FruitDTO.builder().name("partial").build();
        String fDTOJSON = objectMapper.writeValueAsString(fDTO);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/fruit/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(fDTOJSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(1L)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("partial")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.fruitClass").value("APPLE_PEAR")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(0)
        );
    }

    @Test
    public void TestThatDeleteFruitReturnsHttpStatus204() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/fruit/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());

    }

    @Test
    public void TestThatDeleteFruitReturnsHttpStatus204OnExistingFruit() throws Exception {
        Fruit f = TestDataUtil.buildFruitA();
        Fruit saved = fruitService.save(f);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/fruit/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}
