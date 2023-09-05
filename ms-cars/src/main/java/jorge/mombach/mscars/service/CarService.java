package jorge.mombach.mscars.service;

import jorge.mombach.mscars.entity.Car;
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

    public CarDtoResponse createCar(CarDtoRequest carDtoRequest) {
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