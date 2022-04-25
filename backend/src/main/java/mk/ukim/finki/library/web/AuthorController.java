package mk.ukim.finki.library.web;

import mk.ukim.finki.library.model.Author;
import mk.ukim.finki.library.model.dto.AuthorDto;
import mk.ukim.finki.library.model.dto.AuthorSearchDto;
import mk.ukim.finki.library.model.exceptions.AuthorIdentityNotFoundException;
import mk.ukim.finki.library.model.exceptions.AuthorNotFoundException;
import mk.ukim.finki.library.model.exceptions.CountryNotFoundException;
import mk.ukim.finki.library.service.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public List<Author> findAll() {

        return this.authorService.findAll();

    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> findById(@PathVariable Long id) {

        return this.authorService.findById(id)
                .map(author -> ResponseEntity.ok().body(author))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}")
    public List<Author> findByName(@PathVariable String name) {

        return this.authorService.findByName(name);

    }

    @GetMapping("/surname/{surname}")
    public List<Author> findBySurname(@PathVariable String surname) {

        return this.authorService.findBySurname(surname);

    }

    @GetMapping("/country/{country}")
    public List<Author> findByCountry(@PathVariable String country) {

        List<Author> authors = new ArrayList<>();
        try {
            authors = this.authorService.findByCountry(country);
        } catch (CountryNotFoundException e) {
            System.out.printf("Exception: %s%n", e.getMessage());
        }
        return authors;

    }

    @GetMapping("/author")
    public ResponseEntity<Author> findByNameAndSurname(@RequestBody AuthorSearchDto authorSearchDto) {

        Author author;
        try {
            author = this.authorService
                    .findByNameAndSurname(authorSearchDto.getName(), authorSearchDto.getSurname());
            return ResponseEntity.ok().body(author);
        } catch (AuthorIdentityNotFoundException e) {
            System.out.printf("Exception: %s%n", e.getMessage());
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping("/add")
    public ResponseEntity<Author> save(@RequestBody AuthorDto authorDto) {

        return this.authorService.save(authorDto)
                .map(author -> ResponseEntity.ok().body(author))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Author> edit(@PathVariable Long id, @RequestBody AuthorDto authorDto) {

        return this.authorService.edit(id, authorDto)
                .map(author -> ResponseEntity.ok().body(author))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Author> delete(@PathVariable Long id) {

        this.authorService.deleteById(id);
        if (this.authorService.findById(id).isEmpty()) return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();

    }

}
