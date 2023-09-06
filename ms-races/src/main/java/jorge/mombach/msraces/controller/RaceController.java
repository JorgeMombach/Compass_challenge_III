package jorge.mombach.msraces.controller;

import jakarta.validation.Valid;
import jorge.mombach.msraces.payload.RaceDtoRequest;
import jorge.mombach.msraces.payload.RaceDtoResponse;
import jorge.mombach.msraces.service.RaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/race")
public class RaceController {

    @Autowired
    RaceService raceService;

    @PostMapping
    public ResponseEntity<RaceDtoResponse> createCar(@Valid @RequestBody RaceDtoRequest raceDtoRequest) {
        RaceDtoResponse createdCar = raceService.createRace(raceDtoRequest);
        return new ResponseEntity<>(createdCar, HttpStatus.CREATED);
    }

    @GetMapping
    public List<RaceDtoResponse> getAllCars(){
        return raceService.getAllRaces();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RaceDtoResponse> getCarById(@PathVariable String id) {
        RaceDtoResponse car = raceService.getRaceById(id);
        if (car != null) {
            return ResponseEntity.ok(car);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RaceDtoResponse> updateCar(@PathVariable String id, @Valid @RequestBody RaceDtoRequest raceDtoRequest) {
        RaceDtoResponse updatedCar = raceService.updateRace(id, raceDtoRequest);
        if (updatedCar != null) {
            return ResponseEntity.ok(updatedCar);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRace(@PathVariable String id) {
        String result = raceService.deleteRace(id);
        if (result.equals("Race deleted.")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
