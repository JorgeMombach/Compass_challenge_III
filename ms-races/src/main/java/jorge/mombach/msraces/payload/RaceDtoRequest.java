package jorge.mombach.msraces.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class RaceDtoRequest {

    private String name;
    private String country;
    @JsonFormat(pattern = "dd/mm/yyyy")
    private Date date;
    private String status;
    private List<CarDtoResponse> cars;
}
