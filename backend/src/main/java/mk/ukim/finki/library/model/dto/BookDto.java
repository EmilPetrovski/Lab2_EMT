package mk.ukim.finki.library.model.dto;

import lombok.Data;

@Data
public class BookDto {

    private String name;

    private String category;

    private Long author;

    private Integer availableCopies;

    public BookDto(String name, String category, Long author, Integer availableCopies) {
        this.name = name;
        this.category = category;
        this.author = author;
        this.availableCopies = availableCopies;
    }
}
