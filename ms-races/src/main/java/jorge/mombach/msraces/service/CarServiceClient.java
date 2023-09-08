package jorge.mombach.msraces.service;

import jorge.mombach.msraces.payload.CarDtoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "ms-cars", url = "localhost:8080")
public interface CarServiceClient {

    @GetMapping("/api/car")
    public List<CarDtoResponse> getAllCars();
}


