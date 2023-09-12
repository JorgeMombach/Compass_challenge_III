package jorge.mombach.mshistory.service;

import jorge.mombach.mshistory.entity.RaceResult;
import jorge.mombach.mshistory.payload.RaceResultDto;
import jorge.mombach.mshistory.repository.RaceResultRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.*;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


import static org.mockito.Mockito.when;

class RaceResultConsumerTest {


    @InjectMocks
    private RaceResultConsumer raceResultConsumer;

    @Mock
    private RaceResultRepository raceResultRepository;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllRaces() {
        RaceResult raceResult1 = new RaceResult();
        raceResult1.setId("1");
        raceResult1.setName("Race 1");
        raceResult1.setCountry("Country 1");
        raceResult1.setDate(new Date());
        raceResult1.setStatus("finished");
        raceResult1.setCars(new ArrayList<>());
        raceResult1.setDateOfInsertion(new Date());

        RaceResult raceResult2 = new RaceResult();
        raceResult2.setId("2");
        raceResult2.setName("Race 2");
        raceResult2.setCountry("Country 2");
        raceResult2.setDate(new Date());
        raceResult2.setStatus("started");
        raceResult2.setCars(new ArrayList<>());
        raceResult2.setDateOfInsertion(new Date());

        when(raceResultRepository.findAll()).thenReturn(List.of(raceResult1, raceResult2));

        List<RaceResultDto> raceResults = raceResultConsumer.getAllRaces();

        assertNotNull(raceResults);
        assertFalse(raceResults.isEmpty());
        assertEquals(2, raceResults.size());

        verify(raceResultRepository, times(1)).findAll();
    }


    @Test
    public void testGetRaceById() {
        String raceId = "1";

        RaceResult raceResult = new RaceResult();
        raceResult.setId(raceId);
        raceResult.setName("Tets race");
        raceResult.setCountry("Test country");
        raceResult.setDate(new Date());
        raceResult.setStatus("finished");
        raceResult.setCars(new ArrayList<>());
        raceResult.setDateOfInsertion(new Date());

        when(raceResultRepository.findById(raceId)).thenReturn(Optional.of(raceResult));

        RaceResultDto raceResultDto = new RaceResultDto();
        when(modelMapper.map(raceResult, RaceResultDto.class)).thenReturn(raceResultDto);

        RaceResultDto result = raceResultConsumer.getRaceById(raceId);

        assertNotNull(result);

        verify(raceResultRepository, times(1)).findById(raceId);

        verify(modelMapper, times(1)).map(raceResult, RaceResultDto.class);
    }

    @Test
    public void testGetRaceByIdNotFound() {
        String raceId = "999";

        when(raceResultRepository.findById(raceId)).thenReturn(Optional.empty());

        RaceResultDto result = raceResultConsumer.getRaceById(raceId);

        assertNull(result);

        verify(raceResultRepository, times(1)).findById(raceId);

    }
}