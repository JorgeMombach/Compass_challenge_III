package jorge.mombach.msraces.service;

import jorge.mombach.msraces.entity.Race;
import jorge.mombach.msraces.payload.CarDtoResponse;
import jorge.mombach.msraces.payload.RaceDtoRequest;
import jorge.mombach.msraces.payload.RaceDtoResponse;
import jorge.mombach.msraces.payload.RaceInfoDto;
import jorge.mombach.msraces.repository.RaceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class RaceService {

    @Autowired
    private RaceRepository raceRepository;

    @Autowired
    private ModelMapper modelMapper;

    private final CarServiceClient carServiceClient;

    @Autowired
    public RaceService(CarServiceClient carServiceClient) {
        this.carServiceClient = carServiceClient;
    }

    public List<CarDtoResponse> getRandomCarsForRace() {
        List<CarDtoResponse> allCars = carServiceClient.getAllCars();

        Collections.shuffle(allCars);

        Random random = new Random();
        int numCarsToSelect = random.nextInt(8) + 3;

        List<CarDtoResponse> selectedCars = new ArrayList<>();

        for (int i = 0; i < numCarsToSelect && i < allCars.size(); i++) {
            CarDtoResponse randomCar = allCars.get(i);
            selectedCars.add(randomCar);
        }

        return selectedCars;
    }

    public RaceDtoResponse createRace(RaceDtoRequest raceDtoRequest) {
        List<CarDtoResponse> randomCars = getRandomCarsForRace();

        Race race = modelMapper.map(raceDtoRequest, Race.class);
        race.setCars(randomCars);
        Race savedRace = raceRepository.save(race);

        return modelMapper.map(savedRace, RaceDtoResponse.class);
    }

    public List<RaceInfoDto> getAllRaces(){
        List<Race> races = raceRepository.findAll();
        return races.stream()
                .map(car -> modelMapper.map(car, RaceInfoDto.class))
                .collect(Collectors.toList());
    }

    public RaceDtoResponse getRaceById(String id){
        Race race = raceRepository.findById(id).orElse(null);
        if (race == null){
            return null;
        }
        return modelMapper.map(race, RaceDtoResponse.class);
    }

    public RaceInfoDto updateRace(String id, RaceDtoRequest raceDtoRequest) {
        Race race = raceRepository.findById(id).orElse(null);
        if (race == null) {
            return null;
        }

        race.setName(raceDtoRequest.getName());
        race.setCountry(raceDtoRequest.getCountry());
        race.setDate(raceDtoRequest.getDate());

        Race updatedRace = raceRepository.save(race);

        return modelMapper.map(updatedRace, RaceInfoDto.class);
    }

    public String deleteRace (String id){
        raceRepository.deleteById(id);
        return "Race deleted.";
    }

    public RaceDtoResponse overtake(String raceId, String carId) {
        Race race = raceRepository.findById(raceId).orElse(null);

        if (race == null) {
            return null;
        }

        List<CarDtoResponse> cars = race.getCars();
        int carIndex = -1;

        for (int i = 0; i < cars.size(); i++) {
            if (cars.get(i).getId().equals(carId)) {
                carIndex = i;
                break;
            }
        }

        if (carIndex == -1) {
            return null;
        }

        if (carIndex > 0) {
            CarDtoResponse currentCar = cars.get(carIndex);
            CarDtoResponse carInFront = cars.get(carIndex - 1);
            cars.set(carIndex, carInFront);
            cars.set(carIndex - 1, currentCar);
        }

        race.setFinalResult(new ArrayList<>(cars));

        raceRepository.save(race);

        return modelMapper.map(race, RaceDtoResponse.class);
    }

    public List<CarDtoResponse> getRaceResult(String raceId) {
        Race race = raceRepository.findById(raceId).orElse(null);
        if (race == null || race.getFinalResult() == null) {
            return null;
        }
        return race.getFinalResult();
    }

}
