import React, {Component} from "react";
import {BrowserRouter as Router, Redirect, Route, Routes, Navigate} from "react-router-dom";
import Header from "../Header/header";
import Authors from "../Authors/authors";
import Countries from "../Countries/countries";
import Books from "../Books/BooksList/books";
import BookAdd from "../Books/BooksAdd/bookAdd";
import BookEdit from "../Books/BooksEdit/bookEdit";
import Categories from "../Categories/categories";
import LibraryService from "../../repository/libraryRepository";
import Register from "../User/Register/register";

class App extends Component {

    constructor(props) {
        super(props);
        this.state = {
            categories: 
                ["NOVEL", 
                "THRILER", 
                "HISTORY", 
                "FANTASY", 
                "BIOGRAPHY", 
                "CLASSICS", 
                "DRAMA"],
            authors: [],
            selectedBook: {},
            books: [],
            countries: []
        }
    }

    render() {
        return (
            <Router>
                <Header/>
                <main>
                    <div className={"container"}>
                        <Route path={"/register"} exact
                               render={() => <Register onRegister={this.register}/>}
                        />
                        <Route path={"/authors"} exact
                               render={() => <Authors authors={this.state.authors}/>}/>
                        <Route path={"/countries"} exact
                               render={() => <Countries countries={this.state.countries}/>}/>
                        <Route path={"/categories"} exact
                               render={() => <Categories categories={this.state.categories}/>}/>
                        <Route path={"/books/add"} exact
                               render={() => <BookAdd categories={this.state.categories}
                                                      authors={this.state.authors}
                                                      onAddBook={this.addBook}/>}/>
                        <Route path={"/books/edit/:id"} exact
                               render={() => <BookEdit authors={this.state.authors}
                                                       categories={this.state.categories}
                                                       onEditBook={this.editBook}
                                                       book={this.state.selectedBook}/>}/>

                        <Route path={"/books"} exact
                               render={() => <Books books={this.state.books}
                                                    onDelete={this.deleteBook}
                                                    onEdit={this.getBook}
                                                    rentBook={this.rentBook}
                                                    returnBook={this.returnBook}/>}/>
                        <Redirect to={"/books"}/>
                    </div>
                </main>
            </Router>
        );
    }


    componentDidMount() {
        this.loadBooks();
        this.loadAuthors();
        this.loadCountries();
    }

    loadBooks = () => {
        LibraryService.fetchBooks()
            .then((data) => {
                this.setState({
                    books: data.data
                })
            })
    }

    addBook = (name, category, author, availableCopies) => {
        LibraryService.addBook(name, category, author, availableCopies)
            .then(() => {
                this.loadBooks();
            });
    }

    getBook = (id) => {
        LibraryService.getBook(id)
            .then((data) => {
                this.setState({
                    selectedBook: data.data
                });
            });
    }

    loadAuthors = () => {
        LibraryService.fetchAuthors()
            .then((data) => {
                this.setState({
                    authors: data.data
                })
            });
    }

    loadCountries = () => {
        LibraryService.fetchCountries()
            .then((data) => {
                this.setState({
                    countries: data.data
                })
            })

    }

    loadCategories = () => {
        LibraryService.fetchCategories()
            .then((data) => {
              this.setState({
                categories: data.data
              })
            })
      }


    editBook = (id, name, category, author, availableCopies) => {
        LibraryService.editBook(id, name, category, author, availableCopies)
            .then(() => {
                this.loadBooks();
            });
    }
    
    rentBook = (id) => {
        LibraryService.getBook(id)
            .then((data) => {
                let book = data.data;
                book.availableCopies -= 1;

                this.editBook(book.id, book.name, book.category, book.author.id, book.availableCopies);
            });
    }

    returnBook = (id) => {
        LibraryService.getBook(id)
            .then((data) => {
                let book = data.data;
                book.availableCopies += 1;

                this.editBook(book.id, book.name, book.category, book.author.id, book.availableCopies);
            });
    }

    deleteBook = (id) => {
        LibraryService.deleteBook(id)
            .then(() => {
                this.loadBooks();
            })
    }


    
}

export default App;
