package mk.ukim.finki.library.repository;

import mk.ukim.finki.library.model.Author;
import mk.ukim.finki.library.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    List<Author> findAllByName(String name);

    List<Author> findAllBySurname(String surname);

    List<Author> findAllByCountry(Country country);

    Optional<Author> findAuthorByNameAndSurname(String name, String surname);

    Optional<Author> findAuthorByName(String name);
}
