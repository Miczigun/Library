package pl.polsl.library.model.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PersonalDataDto {
    private String name;
    private String surname;
    private String address;

    public PersonalDataDto(String name, String surname, String address){
        this.name = name;
        this.surname = surname;
        this.address = address;
    }
}
