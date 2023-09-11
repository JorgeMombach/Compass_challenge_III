package jorge.mombach.mshistory.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jorge.mombach.mshistory.entity.RaceResult;
import jorge.mombach.mshistory.payload.RaceResultDto;
import jorge.mombach.mshistory.repository.RaceResultRepository;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RaceResultConsumer {

    @Autowired
    RaceResultRepository raceResultRepository;

    @Autowired
    private ModelMapper modelMapper;

    @RabbitListener(queues = "race-result-queue")
    public void receiveRaceResult(String raceResultJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            RaceResult raceResult = objectMapper.readValue(raceResultJson, RaceResult.class);

            raceResultRepository.save(raceResult);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<RaceResultDto> getAllRaces(){
        List<RaceResult> races = raceResultRepository.findAll();
        return races.stream()
                .map(car -> modelMapper.map(car, RaceResultDto.class))
                .collect(Collectors.toList());
    }
}
