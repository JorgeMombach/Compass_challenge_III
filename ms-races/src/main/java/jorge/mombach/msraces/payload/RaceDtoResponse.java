package jorge.mombach.msraces.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RaceDtoResponse {

    private String id;
    private String name;
    private String country;
    private Date date;
}
