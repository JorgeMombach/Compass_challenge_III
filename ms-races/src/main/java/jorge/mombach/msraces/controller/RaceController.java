package jorge.mombach.msraces.controller;

import jakarta.validation.Valid;
import jorge.mombach.msraces.payload.CarDtoResponse;
import jorge.mombach.msraces.payload.RaceDtoRequest;
import jorge.mombach.msraces.payload.RaceDtoResponse;
import jorge.mombach.msraces.payload.RaceInfoDto;
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
    public ResponseEntity<RaceDtoResponse> createRace(@Valid @RequestBody RaceDtoRequest raceDtoRequest) {
        RaceDtoResponse createdRace = raceService.createRace(raceDtoRequest);
        return new ResponseEntity<>(createdRace, HttpStatus.CREATED);
    }

    @GetMapping
    public List<RaceInfoDto> getAllRaces(){
        return raceService.getAllRaces();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RaceDtoResponse> getRaceById(@PathVariable String id) {
        RaceDtoResponse race = raceService.getRaceById(id);
        if (race != null) {
            return ResponseEntity.ok(race);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RaceInfoDto> updateRace(@PathVariable String id, @Valid @RequestBody RaceDtoRequest raceDtoRequest) {
        RaceInfoDto updatedRace = raceService.updateRace(id, raceDtoRequest);
        if (updatedRace != null) {
            return ResponseEntity.ok(updatedRace);
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

    @PutMapping("/{raceId}/overtake/{carId}")
    public ResponseEntity<RaceDtoResponse> overtake(@PathVariable String raceId, @PathVariable String carId) {
        RaceDtoResponse updatedRace = raceService.overtake(raceId, carId);
        if (updatedRace != null) {
            return ResponseEntity.ok(updatedRace);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{raceId}/result")
    public ResponseEntity<List<CarDtoResponse>> getRaceResult(@PathVariable String raceId) {
        List<CarDtoResponse> raceResult = raceService.getRaceResult(raceId);
        if (raceResult != null) {
            return ResponseEntity.ok(raceResult);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}