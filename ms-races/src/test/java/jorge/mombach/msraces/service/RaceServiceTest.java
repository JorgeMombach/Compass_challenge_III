package jorge.mombach.msraces.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import jorge.mombach.msraces.payload.CarDtoResponse;
import jorge.mombach.msraces.payload.PilotDto;
import jorge.mombach.msraces.payload.RaceInfoDto;
import jorge.mombach.msraces.repository.RaceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import jorge.mombach.msraces.entity.Race;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class RaceServiceTest {

    @InjectMocks
    private RaceService raceService;

    @Mock
    private RaceRepository raceRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private CarServiceClient carServiceClient;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testConvertRaceToJson() throws JsonProcessingException {
        CarDtoResponse car1 = new CarDtoResponse();
        car1.setId("1");
        car1.setBrand("Brand 1");
        car1.setModel("Model 1");
        car1.setYear(new Date());

        CarDtoResponse car2 = new CarDtoResponse();
        car2.setId("2");
        car2.setBrand("Brand 2");
        car2.setModel("Model 2");
        car2.setYear(new Date());

        PilotDto pilot1 = new PilotDto();
        pilot1.setName("Pilot 1");
        pilot1.setAge(30);

        PilotDto pilot2 = new PilotDto();
        pilot2.setName("Pilot 2");
        pilot2.setAge(28);

        car1.setPilot(pilot1);
        car2.setPilot(pilot2);

        Race race = new Race();
        race.setId("123");
        race.setName("Race 1");
        race.setCountry("Country 1");
        race.setDate(new Date());
        race.setStatus("Started");

        List<CarDtoResponse> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        race.setCars(cars);

        when(modelMapper.map(race, Race.class)).thenReturn(race);

        String raceJson = raceService.convertRaceToJson(race);

        ObjectMapper objectMapper = new ObjectMapper();
        String expectedJson = objectMapper.writeValueAsString(race);

        assertEquals(expectedJson, raceJson);
    }
}