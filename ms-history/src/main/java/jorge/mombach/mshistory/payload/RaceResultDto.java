package jorge.mombach.mshistory.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import jorge.mombach.mshistory.entity.Car;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RaceResultDto {

    private String id;
    private String name;
    private String country;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date date;
    private String status;
    private List<Car> cars;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dateOfInsertion;
}
