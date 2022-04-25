package mk.ukim.finki.library.model.dto;

import lombok.Data;

@Data
public class CountryDto {

    private String name;

    public CountryDto(String name, String continent) {
        this.name = name;
    }
}
