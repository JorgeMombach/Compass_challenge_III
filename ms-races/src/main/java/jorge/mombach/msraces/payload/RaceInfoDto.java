package jorge.mombach.msraces.payload;

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
public class RaceInfoDto {
    private String id;
    private String name;
    private String country;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date date;
}
