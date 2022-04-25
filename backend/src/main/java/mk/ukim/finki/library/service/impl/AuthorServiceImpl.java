package mk.ukim.finki.library.service.impl;

import mk.ukim.finki.library.model.Author;
import mk.ukim.finki.library.model.Country;
import mk.ukim.finki.library.model.dto.AuthorDto;
import mk.ukim.finki.library.model.exceptions.AuthorIdentityNotFoundException;
import mk.ukim.finki.library.model.exceptions.AuthorNotFoundException;
import mk.ukim.finki.library.model.exceptions.CountryNotFoundException;
import mk.ukim.finki.library.repository.AuthorRepository;
import mk.ukim.finki.library.repository.CountryRepository;
import mk.ukim.finki.library.service.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final CountryRepository countryRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository,
                             CountryRepository countryRepository) {
        this.authorRepository = authorRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    public List<Author> findAll() {
        return this.authorRepository.findAll();
    }

    @Override
    public Optional<Author> findById(Long id) {
        return this.authorRepository.findById(id);
    }

    @Override
    public List<Author> findByName(String name) {
        return this.authorRepository.findAllByName(name);
    }

    @Override
    public List<Author> findBySurname(String surname) {
        return this.authorRepository.findAllBySurname(surname);
    }

    // TODO: Might now work...
    @Override
    public List<Author> findByCountry(String country) {
        Country c = this.countryRepository.findByName(country)
                .orElseThrow(() -> new CountryNotFoundException(country));
        return this.authorRepository.findAllByCountry(c);
    }

    @Override
    public Author findByNameAndSurname(String name, String surname) {
        return this.authorRepository.findAuthorByNameAndSurname(name, surname)
                .orElseThrow(() -> new AuthorIdentityNotFoundException(name, surname));
    }

    @Override
    public Optional<Author> save(AuthorDto authorDto) {
        Country country = this.countryRepository.findById(authorDto.getCountry())
                .orElseThrow(() -> new CountryNotFoundException(authorDto.getCountry()));
        this.authorRepository.
                findAuthorByNameAndSurname(authorDto.getName(), authorDto.getSurname())
                .ifPresent(author -> this.authorRepository.deleteById(author.getId()));

        Author author = new Author(authorDto.getName(), authorDto.getSurname(), country);
        this.authorRepository.save(author);
        return Optional.of(author);
    }

    @Override
    public Optional<Author> edit(Long id, AuthorDto authorDto) {
        Author author = this.authorRepository.findById(id).orElseThrow(AuthorNotFoundException::new);

        author.setName(authorDto.getName());
        author.setSurname(authorDto.getSurname());
        Country country = this.countryRepository.findById(authorDto.getCountry())
                .orElseThrow(() -> new CountryNotFoundException(authorDto.getCountry()));
        author.setCountry(country);

        this.authorRepository.save(author);

        return Optional.of(author);
    }

    @Override
    public void deleteById(Long id) {
        this.authorRepository.deleteById(id);
    }
}
