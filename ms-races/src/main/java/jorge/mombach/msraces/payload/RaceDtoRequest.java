package jorge.mombach.msraces.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class RaceDtoRequest {

    private String name;
    private String country;
    @JsonFormat(pattern = "dd/mm/yyyy")
    private Date date;
}
