package jorge.mombach.mshistory.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Document(collection = "race_results")

public class RaceResult {

    @Id
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
