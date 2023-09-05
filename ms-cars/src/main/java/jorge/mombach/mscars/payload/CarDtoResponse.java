package jorge.mombach.mscars.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarDtoResponse {

    private String id;
    private String brand;
    private String model;
    private int year;
    private PilotDto pilot;
}