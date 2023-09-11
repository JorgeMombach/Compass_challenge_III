package jorge.mombach.msraces.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jorge.mombach.msraces.payload.CarDtoResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Document(collection = "race")
public class Race {

    @Id
    private String id;
    private String name;
    private String country;
    @JsonFormat(pattern = "dd/mm/yyyy")
    private Date date;
    private List<CarDtoResponse> cars;
    private List<CarDtoResponse> finalResult;
}
