package mk.ukim.finki.library.service.impl;

import mk.ukim.finki.library.model.Author;
import mk.ukim.finki.library.model.Book;
import mk.ukim.finki.library.model.dto.BookDto;
import mk.ukim.finki.library.model.enumerations.BookCategory;
import mk.ukim.finki.library.model.exceptions.AuthorNotFoundException;
import mk.ukim.finki.library.model.exceptions.BookNotFoundException;
import mk.ukim.finki.library.repository.AuthorRepository;
import mk.ukim.finki.library.repository.BookRepository;
import mk.ukim.finki.library.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookServiceImpl(BookRepository bookRepository,
                           AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    // TODO: Implement this one!
    @Override
    public List<Book> findAll() {

        return this.bookRepository.findAll();

    }

    @Override
    public Optional<Book> findById(Long id) {

        return this.bookRepository.findById(id);

    }

    @Override
    public Optional<Book> findByName(String name) {

        return this.bookRepository.findByName(name);

    }

    @Override
    public List<Book> findByAuthor(String name) {

        Author author = this.authorRepository.findAuthorByName(name)
                .orElseThrow(AuthorNotFoundException::new);
        return this.bookRepository.findAllByAuthor(author);

    }

    // TODO: Might not work...
    @Override
    public List<Book> findByCategory(String category) {

        return this.bookRepository.findAllByCategory(category);

    }

    @Override
    public Optional<Book> save(BookDto bookDto) {

        Author author = this.authorRepository.findById(bookDto.getAuthor())
                .orElseThrow(AuthorNotFoundException::new);

        Book book = new Book(bookDto.getName(), BookCategory.valueOf(bookDto.getCategory()), author, bookDto.getAvailableCopies());
        this.bookRepository.save(book);

        return Optional.of(book);

    }

    @Override
    public Optional<Book> edit(Long id, BookDto bookDto) {

        Book book = this.bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));


        book.setName(bookDto.getName());
        book.setAvailableCopies(bookDto.getAvailableCopies());

        book.setCategory(BookCategory.valueOf(bookDto.getCategory()));

        Author author = this.authorRepository.findById(bookDto.getAuthor())
                .orElseThrow(AuthorNotFoundException::new);
        book.setAuthor(author);
        this.bookRepository.save(book);
        return Optional.of(book);

    }

    @Override
    public void deleteById(Long id) {

        this.bookRepository.deleteById(id);

    }
}
