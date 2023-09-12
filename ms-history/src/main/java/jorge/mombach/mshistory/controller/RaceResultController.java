package jorge.mombach.mshistory.controller;

import jorge.mombach.mshistory.payload.RaceResultDto;
import jorge.mombach.mshistory.service.RaceResultConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/history")
public class RaceResultController {

    @Autowired
    RaceResultConsumer raceResultConsumer;

    @GetMapping
    public List<RaceResultDto> getAllRaces(){
        return raceResultConsumer.getAllRaces();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RaceResultDto> getRaceById(@PathVariable String id) {
        RaceResultDto race = raceResultConsumer.getRaceById(id);
        if (race != null) {
            return ResponseEntity.ok(race);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
