package mk.ukim.finki.library.repository;

import mk.ukim.finki.library.model.Author;
import mk.ukim.finki.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByName(String name);

    List<Book> findAllByAuthor(Author author);

    List<Book> findAllByCategory(String category);

}
