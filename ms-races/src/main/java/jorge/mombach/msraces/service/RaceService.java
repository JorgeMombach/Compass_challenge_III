package jorge.mombach.msraces.service;

import jorge.mombach.msraces.entity.Race;
import jorge.mombach.msraces.payload.RaceDtoRequest;
import jorge.mombach.msraces.payload.RaceDtoResponse;
import jorge.mombach.msraces.repository.RaceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RaceService {

    @Autowired
    private RaceRepository raceRepository;

    @Autowired
    private ModelMapper modelMapper;

    public RaceDtoResponse createRace (RaceDtoRequest raceDtoRequest){
        Race race = modelMapper.map(raceDtoRequest, Race.class);
        Race savedRace = raceRepository.save(race);
        return modelMapper.map(savedRace, RaceDtoResponse.class);
    }

    public List<RaceDtoResponse> getAllRaces(){
        List<Race> races = raceRepository.findAll();
        return races.stream()
                .map(car -> modelMapper.map(car, RaceDtoResponse.class))
                .collect(Collectors.toList());
    }

    public RaceDtoResponse getRaceById(String id){
        Race race = raceRepository.findById(id).orElse(null);
        if (race == null){
            return null;
        }
        return modelMapper.map(race, RaceDtoResponse.class);
    }

    public RaceDtoResponse updateRace(String id, RaceDtoRequest raceDtoRequest){
        Race race = raceRepository.findById(id).orElse(null);
        if (race == null){
            return null;
        }

        modelMapper.map(raceDtoRequest, race);
        Race updatedRace = raceRepository.save(race);

        return modelMapper.map(updatedRace, RaceDtoResponse.class);
    }

    public String deleteRace (String id){
        raceRepository.deleteById(id);
        return "Race deleted.";
    }
}
