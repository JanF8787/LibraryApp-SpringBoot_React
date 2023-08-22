import React, { useEffect, useState } from 'react';
import '../App.css';

export default function Loans() {
  const [loanList, setLoanList] = useState([]);
  const [books, setBooks] = useState([]);
  const [readers, setReaders] = useState([]);
  const [selectedBook, setSelectedBook] = useState("");
  const [selectedReader, setSelectedReader] = useState("");

  useEffect(() => {
    fetch("http://localhost:8080/loans/all", {
      method: "GET",
      headers: { 'Content-Type': 'application/json' }
    })
      .then(res => {
        if (res.ok) {
          res.json()
            .then(data => setLoanList(data));
        }
      })
      .catch(e => console.log(e.message));

    fetch("http://localhost:8080/books/not_borrowed_books", {
      method: "GET",
      headers: { 'Content-Type': 'application/json' }
    })
      .then(res => res.json())
      .then(data => setBooks(data))
      .catch(e => console.log(e));

    fetch("http://localhost:8080/readers/all", {
      method: "GET",
      headers: { 'Content-Type': 'application/json' }
    })
      .then(res => res.json())
      .then(data => setReaders(data))
      .catch(e => console.log(e));
  }, []);

  const addLoan = () => {
    fetch(`http://localhost:8080/loans/add/${selectedBook}/${selectedReader}`, {
      method: "POST",
      headers: { 'Content-Type': 'application/json' }
    })
      .then(res => {
        if (res.ok) {
          res.json()
            .then(data => {
              setLoanList([...loanList, data]);
              setSelectedBook("");
              setSelectedReader("");
            });
        }
      })
      .catch(e => console.log(e));
  }

  const returnBook = (loanId) => {
    fetch(`http://localhost:8080/loans/return/${loanId}`, {
      method: "PATCH",
      headers: { 'Content-Type': 'application/json' }
    })
      .then(res => {
        if (res.ok) {
          res.json()
            .then(data => {

              const updatedLoan = loanList.map(loan => {
                if (loan.id === loanId) {
                  loan.returnDate = data.returnDate;
                  setBooks([...books, data.book]);
                }
                return loan;
              });
              setLoanList(updatedLoan);
            });
        }
      })
      .catch(e => console.log(e));
  }


  return (
    <div className='library'>

      <div className='form-div loan-div'>

        <select value={selectedBook} onChange={e => setSelectedBook(e.target.value)}>
          <option value="">Select a book</option>
          {books.map(book => (
            <option key={book.id} value={book.id}>{book.title}</option>
          ))}
        </select>

        <select value={selectedReader} onChange={e => setSelectedReader(e.target.value)}>
          <option value="">Select a reader</option>
          {readers.map(reader => (
            <option key={reader.id} value={reader.id}>{reader.firstName} {reader.lastName}</option>
          ))}
        </select>

        <button type="submit" className="btn btn-outline-danger" onClick={addLoan}>Borrow Book</button>

      </div>

      <div className='table-div'>

        <h3>Loans</h3>

        <table className='table table-striped table-hover'>
          <tbody className='table-group'>
            <tr className='table-danger'>
              <th>Id</th>
              <th>Reader</th>
              <th>Book</th>
              <th>Borrow Date</th>
              <th>Return Date</th>
              <th></th>
            </tr>
            {loanList.map(loan => (
              <tr className='table-light' key={loan.id}>
                <td>{loan.id}</td>
                <td>{loan.reader.firstName + " " + loan.reader.lastName}</td>
                <td>{loan.book.title}</td>
                <td>{loan.borrowDate.split("-").reverse().join(".")}</td>
                <td>{loan.returnDate === null ? " " : loan.returnDate.split("-").reverse().join(".")}</td>
                <td>
                  {loan.returnDate ? "" : (
                    <div>
                      <button className='btn btn-outline-danger' style={{ marginLeft: '10px' }} onClick={() => returnBook(loan.id)}>Return</button>
                    </div>
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>

      </div>

    </div>
  )
}