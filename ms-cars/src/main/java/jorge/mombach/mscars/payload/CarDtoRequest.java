package jorge.mombach.mscars.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarDtoRequest {

    private String brand;
    private String model;
    private int year;
    private PilotDto pilot;
}
