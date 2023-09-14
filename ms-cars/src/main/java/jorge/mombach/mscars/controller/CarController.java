package jorge.mombach.mscars.controller;

import jakarta.validation.Valid;
import jorge.mombach.mscars.payload.CarDtoRequest;
import jorge.mombach.mscars.payload.CarDtoResponse;
import jorge.mombach.mscars.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/car")
public class CarController {

    @Autowired
    CarService carService;

    @PostMapping
    public ResponseEntity<CarDtoResponse> createCar(@Valid @RequestBody CarDtoRequest carDtoRequest) {
        CarDtoResponse createdCar = carService.createCar(carDtoRequest);
        return new ResponseEntity<>(createdCar, HttpStatus.CREATED);
    }

    @GetMapping
    public List<CarDtoResponse> getAllCars(){
        return carService.getAllCars();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDtoResponse> getCarById(@PathVariable String id) {
        CarDtoResponse car = carService.getCarById(id);
        if (car != null) {
            return ResponseEntity.ok(car);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarDtoResponse> updateCar(@PathVariable String id, @Valid @RequestBody CarDtoRequest carDtoRequest) {
        CarDtoResponse updatedCar = carService.updateCar(id, carDtoRequest);
        if (updatedCar != null) {
            return ResponseEntity.ok(updatedCar);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCar(@PathVariable String id) {
        String result = carService.deleteCar(id);
        if (result.equals("Car deleted.")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
