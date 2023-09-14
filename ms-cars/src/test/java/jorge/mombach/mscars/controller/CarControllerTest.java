package jorge.mombach.mscars.controller;

import jorge.mombach.mscars.payload.CarDtoResponse;
import jorge.mombach.mscars.service.CarService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    @Test
    public void testGetCarById_ValidId() throws Exception {
        String validId = "1";
        CarDtoResponse carDtoResponse = new CarDtoResponse();
        carDtoResponse.setId(validId);
        carDtoResponse.setBrand("Brand1");
        carDtoResponse.setModel("Model1");
        carDtoResponse.setYear(new Date());

        Mockito.when(carService.getCarById(validId)).thenReturn(carDtoResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/car/{id}", validId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(validId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.brand").value("Brand1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model").value("Model1"));
    }

    @Test
    public void testGetCarById_InvalidId() throws Exception {
        String invalidId = "999";
        Mockito.when(carService.getCarById(invalidId)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/car/{id}", invalidId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testDeleteCar_InvalidId() throws Exception {
        String invalidId = "999";

        Mockito.when(carService.deleteCar(invalidId)).thenReturn("Car not found.");

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/car/{id}", invalidId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}