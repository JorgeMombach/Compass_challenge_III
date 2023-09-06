package jorge.mombach.msraces.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class RaceDtoRequest {

    private String name;
    private String country;
    private Date date;
}
