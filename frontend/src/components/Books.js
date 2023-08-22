import React, { useEffect, useState } from 'react';
import '../App.css';

export default function Books() {
  const [bookList, setBookList] = useState([]);
  const [title, setTitle] = useState("");
  const [author, setAuthor] = useState("");
  const [editId, setEditId] = useState(-1);
  const [buttonText, setButtonText] = useState("Add book");

  useEffect(() => {
    fetch("http://localhost:8080/books/all_with_borrowers", {
      method: "GET",
      headers: { 'Content-Type': 'application/json' },
    })
      .then(res => {
        if (res.ok) {
          res.json()
            .then(data => setBookList(data));
        }
      })
      .catch(e => console.log(e.message));
  }, []);

  const addBook = () => {
    const newBook = {
      title: title,
      author: author,
    };

    if (editId < 1) {
      fetch("http://localhost:8080/books/add_book", {
        method: "POST",
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(newBook)
      })
        .then(res => {
          if (res.ok) {
            res.json()
              .then(data => setBookList([...bookList, data]));
          }
        })
        .catch(e => console.log(e));
    } else {
      editBook(editId);
    }
  }

  const editBook = (editId) => {
    const editBook = {
      title: title,
      author: author,
    };

    fetch(`http://localhost:8080/books/edit/${editId}`, {
      method: "PATCH",
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(editBook)
    })
      .then(res => {
        if (res.ok) {
          res.json()
            .then(data => {

              const updatedBook = bookList.map(book => {
                if (book.id === editId) {
                  book.title = data.title;
                  book.author = data.author;
                }
                return book;
              });

              setBookList(updatedBook);
              setEditId(-1);
              setButtonText("Add book");
            });
        }
      })
      .catch(e => console.log(e));
  }

  const editBtn = (bookId) => {
    const myBook = bookList.filter(book => book.id === bookId)[0];

    if (myBook) {
      setTitle(myBook.title);
      setAuthor(myBook.author);
      setEditId(bookId);
      setButtonText(`Edit book: ${myBook.title}`)
    }
  }

  return (
    <div className='library'>

      <div className='form-div'>

        <form className="row row-cols-lg-auto g-3 align-items-center">

          <div className="col-12">
            <input type="text" className="form-control" name="title" placeholder='add title'
              value={title} onChange={e => setTitle(e.target.value)}
            />
          </div>

          <div className="col-12">
            <input type="text" className="form-control" name="author" placeholder='add author'
              value={author} onChange={e => setAuthor(e.target.value)}
            />
          </div>

          <div className="col-12" style={{ display: 'flex', justifyContent: 'center' }}>
            <button type="submit" className="btn btn-outline-danger" onClick={addBook}>{buttonText}</button>
          </div>

        </form>

      </div>

      <div className='table-div'>

        <h3>Books</h3>

        <table className='table table-striped table-hover'>
          <tbody className='table-group'>
            <tr className='table-danger'>
              <th>Id</th>
              <th>Title</th>
              <th>Author</th>
              <th>Borrowed</th>
              <th>Borrower</th>
              <th></th>
            </tr>
            {bookList.map(book => (
              <tr className='table-light' key={book.id}>
                <td>{book.id}</td>
                <td>{book.title}</td>
                <td>{book.author}</td>
                <td>{book.borrowed ? "Yes" : "No"}</td>
                <td>{book.borrowed ? book.borrower : ""}</td>
                <td>
                  <div>
                    <button className='btn btn-outline-danger' style={{ marginLeft: '10px' }} onClick={() => editBtn(book.id)}>Edit</button>
                  </div>
                </td>
              </tr>
            ))}
          </tbody>
        </table>

      </div>

    </div>
  )
}