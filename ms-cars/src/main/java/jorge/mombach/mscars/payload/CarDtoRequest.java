package jorge.mombach.mscars.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CarDtoRequest {

    private String brand;
    private String model;
    @JsonFormat(pattern = "yyyy")
    private Date year;
    private PilotDto pilot;
}
