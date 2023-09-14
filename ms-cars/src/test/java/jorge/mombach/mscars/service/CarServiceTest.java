package jorge.mombach.mscars.service;

import jorge.mombach.mscars.entity.Car;
import jorge.mombach.mscars.entity.Pilot;
import jorge.mombach.mscars.payload.CarDtoRequest;
import jorge.mombach.mscars.payload.CarDtoResponse;
import jorge.mombach.mscars.payload.PilotDto;
import jorge.mombach.mscars.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

class CarServiceTest {

    @InjectMocks
    private CarService carService;

    @Mock
    private CarRepository carRepository;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testIsDuplicatePilot() {
        when(carRepository.existsByPilotNameAndPilotAge(anyString(), anyInt())).thenReturn(true);

        boolean result = carService.isDuplicatePilot("PilotName", 30);

        assertTrue(result);
    }

    @Test
    public void testIsNotDuplicatePilot() {
        when(carRepository.existsByPilotNameAndPilotAge(anyString(), anyInt())).thenReturn(false);

        boolean result = carService.isDuplicatePilot("PilotName", 30);

        assertFalse(result);
    }

    @Test
    public void testIsDuplicateCar() {
        CarDtoRequest carDtoRequest = new CarDtoRequest();
        carDtoRequest.setBrand("Brand");
        carDtoRequest.setModel("Model");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        try {
            Date year = sdf.parse("2023");
            carDtoRequest.setYear(year);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        when(carRepository.existsByBrandAndModelAndYear("Brand", "Model", carDtoRequest.getYear())).thenReturn(true);

        boolean result = carService.isDuplicateCar(carDtoRequest);

        assertTrue(result);
    }

    @Test
    public void testIsNotDuplicateCar() {
        CarDtoRequest carDtoRequest = new CarDtoRequest();
        carDtoRequest.setBrand("Brand");
        carDtoRequest.setModel("Model");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        try {
            Date year = sdf.parse("2023");
            carDtoRequest.setYear(year);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        when(carRepository.existsByBrandAndModelAndYear("Brand", "Model", carDtoRequest.getYear())).thenReturn(false);

        boolean result = carService.isDuplicateCar(carDtoRequest);

        assertFalse(result);
    }

    @Test
    public void testGetAllCars() throws ParseException {
        List<Car> carList = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        Date date1 = createDateWithYear("2022");
        Date date2 = createDateWithYear("2023");

        carList.add(new Car("1", "Brand1", "Model1", date1, new Pilot("Pilot1", 30)));
        carList.add(new Car("2", "Brand2", "Model2", date2, new Pilot("Pilot2", 35)));

        when(carRepository.findAll()).thenReturn(carList);

        CarDtoResponse carDtoResponse1 = new CarDtoResponse("1", "Brand1", "Model1", date1, new PilotDto("Pilot1", 30));
        CarDtoResponse carDtoResponse2 = new CarDtoResponse("2", "Brand2", "Model2", date2, new PilotDto("Pilot2", 35));

        when(modelMapper.map(carList.get(0), CarDtoResponse.class)).thenReturn(carDtoResponse1);
        when(modelMapper.map(carList.get(1), CarDtoResponse.class)).thenReturn(carDtoResponse2);

        List<CarDtoResponse> response = carService.getAllCars();

        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals(carDtoResponse1, response.get(0));
        assertEquals(carDtoResponse2, response.get(1));
    }

    private Date createDateWithYear(String year) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        return dateFormat.parse(year);
    }

    @Test
    public void testGetCarById() throws ParseException {
        String carId = "1";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        Date date1 = createDateWithYear("2022");
        Car car = new Car(carId, "Brand1", "Model1", date1, new Pilot("Pilot1", 30));

        when(carRepository.findById(carId)).thenReturn(java.util.Optional.of(car));

        CarDtoResponse carDtoResponse = new CarDtoResponse(carId, "Brand1", "Model1", date1, new PilotDto("Pilot1", 30));

        when(modelMapper.map(car, CarDtoResponse.class)).thenReturn(carDtoResponse);

        CarDtoResponse response = carService.getCarById(carId);

        assertNotNull(response);
        assertEquals(carDtoResponse, response);
    }

    @Test
    public void testDeleteCar() {
        String carId = "1";

        String result = carService.deleteCar(carId);

        verify(carRepository).deleteById(carId);

        assertEquals("Car deleted.", result);
    }
}


