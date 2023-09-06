package jorge.mombach.mscars.service;

import jorge.mombach.mscars.entity.Car;
import jorge.mombach.mscars.exception.DuplicateCarException;
import jorge.mombach.mscars.exception.DuplicatePilotException;
import jorge.mombach.mscars.payload.CarDtoRequest;
import jorge.mombach.mscars.payload.CarDtoResponse;
import jorge.mombach.mscars.repository.CarRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private ModelMapper modelMapper;

    public boolean isDuplicatePilot(String pilotName, int pilotAge) {
        return carRepository.existsByPilotNameAndPilotAge(pilotName, pilotAge);
    }

    public boolean isDuplicateCar(CarDtoRequest carDtoRequest) {
        return carRepository.existsByBrandAndModelAndYear(
                carDtoRequest.getBrand(),
                carDtoRequest.getModel(),
                carDtoRequest.getYear());
    }

    public CarDtoResponse createCar(CarDtoRequest carDtoRequest) {

        if (isDuplicatePilot(carDtoRequest.getPilot().getName(), carDtoRequest.getPilot().getAge())) {
            throw new DuplicatePilotException("There's already a pilot with this name and age.");
        }

        if (isDuplicateCar(carDtoRequest)) {
            throw new DuplicateCarException("There's an identical car registered already.");
        }

        Car car = modelMapper.map(carDtoRequest, Car.class);
        Car savedCar = carRepository.save(car);
        return modelMapper.map(savedCar, CarDtoResponse.class);
    }

    public List<CarDtoResponse> getAllCars() {
        List<Car> cars = carRepository.findAll();
        return cars.stream()
                .map(car -> modelMapper.map(car, CarDtoResponse.class))
                .collect(Collectors.toList());
    }

    public CarDtoResponse getCarById(String id) {
        Car car = carRepository.findById(id).orElse(null);
        if (car == null) {
            return null;
        }
        return modelMapper.map(car, CarDtoResponse.class);
    }

    public CarDtoResponse updateCar(String id, CarDtoRequest carDtoRequest) {
        Car car = carRepository.findById(id).orElse(null);
        if (car == null) {
            return null;
        }

        modelMapper.map(carDtoRequest, car);
        Car updatedCar = carRepository.save(car);

        return modelMapper.map(updatedCar, CarDtoResponse.class);
    }

    public String deleteCar(String id) {
        carRepository.deleteById(id);
        return "Car deleted.";
    }
}