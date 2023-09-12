package jorge.mombach.mshistory.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import jorge.mombach.mshistory.entity.Pilot;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarDto {

    private String id;
    private String brand;
    private String model;
    @JsonFormat(pattern = "yyyy")
    private Date year;
    private Pilot pilot;
}
