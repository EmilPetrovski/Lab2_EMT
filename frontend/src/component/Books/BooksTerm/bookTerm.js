import React from "react";
import {Link} from "react-router-dom";

const BookTerm = (props) => {
    return (
        <tr>
            <td>{props.term.name}</td>
            <td>{props.term.author.name}</td>
            <td>{props.term.author.surname}</td>
            <td>{props.term.category}</td>
            <td>{props.term.availableCopies}</td>
            <td>
               <a className={"btn btn-success"} onClick={() => props.rentBook(props.term.id)}>Rent book</a>
               <a className={"btn btn-success ml-2"} onClick={() => props.returnBook(props.term.id)}>Return book</a>
            </td>
            <td className={"text-right"}>
                <Link className={"btn btn-info ml-2"} onClick={() => props.onEdit(props.term.id)}
                      to={`/books/edit/${props.term.id}`}>
                    Edit
                </Link>
                <a title={"Delete"} className={"btn btn-danger mx-1"}
                   onClick={() => props.onDelete(props.term.id)}>Delete
                </a>
            </td>
        </tr>
    );
}

export default BookTerm;