package jorge.mombach.mscars.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarDtoResponse {

    private String id;
    private String brand;
    private String model;
    @JsonFormat(pattern = "yyyy")
    private Date year;
    private PilotDto pilot;
}