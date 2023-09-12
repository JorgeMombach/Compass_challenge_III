package jorge.mombach.mshistory.controller;

import jorge.mombach.mshistory.payload.RaceResultDto;
import jorge.mombach.mshistory.service.RaceResultConsumer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RaceResultControllerTest {

    @InjectMocks
    private RaceResultController raceResultController;

    @Mock
    private RaceResultConsumer raceResultConsumer;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(raceResultController).build();
    }

    @Test
    public void testGetAllRaces() throws Exception {
        List<RaceResultDto> raceResults = new ArrayList<>();

        RaceResultDto raceResult1 = new RaceResultDto();
        raceResult1.setId("1");
        raceResult1.setName("Race 1");
        raceResult1.setCountry("Country 1");
        raceResult1.setDate(new Date());
        raceResult1.setStatus("finished");
        raceResult1.setCars(new ArrayList<>());
        raceResult1.setDateOfInsertion(new Date());

        RaceResultDto raceResult2 = new RaceResultDto();
        raceResult2.setId("2");
        raceResult2.setName("Race 2");
        raceResult2.setCountry("Country 2");
        raceResult2.setDate(new Date());
        raceResult2.setStatus("started");
        raceResult2.setCars(new ArrayList<>());
        raceResult2.setDateOfInsertion(new Date());

        raceResults.add(raceResult1);
        raceResults.add(raceResult2);

        when(raceResultConsumer.getAllRaces()).thenReturn(raceResults);

        mockMvc.perform(get("/api/history"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isArray())
                .andReturn();

        verify(raceResultConsumer, times(1)).getAllRaces();
    }


    @Test
    public void testGetRaceById() throws Exception {
        String validRaceId = "1";
        RaceResultDto validRaceResult = createValidRaceResult();

        when(raceResultConsumer.getRaceById(validRaceId)).thenReturn(validRaceResult);

        mockMvc.perform(get("/api/history/{id}", validRaceId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetRaceByIdNotFound() throws Exception {
        String invalidRaceId = "999";

        when(raceResultConsumer.getRaceById(invalidRaceId)).thenReturn(null);

        mockMvc.perform(get("/api/history/{id}", invalidRaceId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    private RaceResultDto createValidRaceResult() {
        RaceResultDto raceResult = new RaceResultDto();
        return raceResult;
    }
}