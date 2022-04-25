package mk.ukim.finki.library.web;

import mk.ukim.finki.library.model.Country;
import mk.ukim.finki.library.model.dto.CountryDto;
import mk.ukim.finki.library.model.exceptions.CountryNotFoundException;
import mk.ukim.finki.library.service.CountryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/countries")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    public List<Country> findAll() {
        return this.countryService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Country> findById(@PathVariable Long id) {

        return this.countryService.findById(id)
                .map(book -> ResponseEntity.ok().body(book))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Country> findByName(@PathVariable String name) {

        return this.countryService.findByName(name)
                .map(book -> ResponseEntity.ok().body(book))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    // TODO: Not working
    @GetMapping("/nameLike/{name}")
    public List<Country> findByNameLike(@PathVariable String name) {

        return this.countryService.findByContainingName(name);

    }

    @GetMapping("/continent/{continent}")
    public List<Country> findByContinent(@PathVariable String continent) {

        return this.countryService.findByContinent(continent);

    }

    @PostMapping("/add")
    public ResponseEntity<Country> save(@RequestBody CountryDto countryDto) {

        return this.countryService.save(countryDto)
                .map(country -> ResponseEntity.ok().body(country))
                .orElseGet(() -> ResponseEntity.badRequest().build());

    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Country> edit(@PathVariable Long id, @RequestBody CountryDto countryDto) {

        return this.countryService.edit(id, countryDto)
                .map(country -> ResponseEntity.ok().body(country))
                .orElseGet(() -> ResponseEntity.badRequest().build());

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Country> delete(@PathVariable Long id) {

        this.countryService.deleteById(id);
        if (this.countryService.findById(id).isEmpty()) return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();

    }

}
