package jorge.mombach.mscars.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Document(collection = "car")
public class Car {

    @Id
    private String id;

    private String brand;
    private String model;
    private int year;

    private Pilot pilot;
}
