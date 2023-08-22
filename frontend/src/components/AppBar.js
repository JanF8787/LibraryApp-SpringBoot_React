import React from 'react';
import { Link } from 'react-router-dom';
import '../App.css';

export default function AppBar() {
  return (
    <div className='app-bar'>

      <h3>Library App</h3>

      <div className='content-bar'>
        <Link className='book-bar' to="/books">Books</Link>
        <Link className='reader-bar' to="/readers">Readers</Link>
        <Link className='loan-bar' to="/loans">Loans</Link>
      </div>

    </div>
  )
}