import React, { useEffect, useState } from 'react';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import '../App.css';

export default function Readers() {
  const [readerList, setReaderList] = useState([]);
  const [idCard, setIdCard] = useState("");
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [dateOfBirth, setDateOfBirth] = useState(null);
  const [editId, setEditId] = useState(-1);
  const [buttonText, setButtonText] = useState("Add reader");

  useEffect(() => {
    fetch("http://localhost:8080/readers/all", {
      method: "GET",
      headers: { 'Content-Type': 'application/json' },
    })
      .then(res => {
        if (res.ok) {
          res.json()
            .then(data => setReaderList(data));
        }
      })
      .catch(e => console.log(e.message));
  }, []);

  const addReader = () => {
    const newReader = {
      idCard: idCard,
      firstName: firstName,
      lastName: lastName,
      dateOfBirth: dateOfBirth
    }

    if (editId < 1) {
      fetch("http://localhost:8080/readers/add_reader", {
        method: "POST",
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(newReader)
      })
        .then(res => {
          if (res.ok) {
            res.json()
              .then(data => setReaderList([...readerList, data]));
          }
        })
        .catch(e => console.log(e));
    } else {
      editReader(editId);
    }
  }

  const editReader = (editId) => {
    const editReader = {
      idCard: idCard,
      firstName: firstName,
      lastName: lastName,
      dateOfBirth: dateOfBirth.toISOString()
    }

    fetch(`http://localhost:8080/readers/edit/${editId}`, {
      method: "PATCH",
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(editReader)
    })
      .then(res => {
        if (res.ok) {
          res.json()
            .then(data => {

              const updatedReader = readerList.map(reader => {
                if (reader.id === editId) {
                  reader.idCard = data.idCard;
                  reader.firstName = data.firstName;
                  reader.lastName = data.lastName;
                  reader.dateOfBirth = data.dateOfBirth;
                }
                return reader;
              });

              setReaderList(updatedReader);
              setEditId(-1)
              setButtonText("Add reader");
            });
        }
      })
      .catch(e => console.log(e));
  }

  const editBtn = (readerId) => {
    const myReader = readerList.filter(reader => reader.id === readerId)[0];

    if (myReader) {
      setIdCard(myReader.idCard);
      setFirstName(myReader.firstName);
      setLastName(myReader.lastName);
      setDateOfBirth(new Date(myReader.dateOfBirth));
      setEditId(readerId);
      setButtonText(`Edit reader: ${myReader.firstName} ${myReader.lastName}`);
    }
  }

  return (
    <div className='library'>


      <div className='form-div'>

        <form className="row row-cols-lg-auto g-3 align-items-center">

          <div className="col-12">
            <input type="text" className="form-control" name="idCard" placeholder='add OP'
              value={idCard} onChange={e => setIdCard(e.target.value)}
            />
          </div>

          <div className="col-12">
            <input type="text" className="form-control" name="firstName" placeholder='add First Name'
              value={firstName} onChange={e => setFirstName(e.target.value)}
            />
          </div>

          <div className="col-12">
            <input type="text" className="form-control" name="lastName" placeholder='add Last Name'
              value={lastName} onChange={e => setLastName(e.target.value)}
            />
          </div>

          <div>
            <DatePicker
              selected={dateOfBirth}
              onChange={(date) => setDateOfBirth(date)}
              placeholderText="Select Date of Birth"
              dateFormat="yyyy-MM-dd"
              showYearDropdown
              scrollableYearDropdown
              yearDropdownItemNumber={100}
            />
          </div>


          <div className="col-12" style={{ display: 'flex', justifyContent: 'center' }}>
            <button type="submit" className="btn btn-outline-danger" onClick={addReader}>{buttonText}</button>
          </div>

        </form>

      </div>

      <div className='table-div'>

        <h3>Readers</h3>

        <table className='table table-striped table-hover'>
          <tbody className='table-group'>
            <tr className='table-danger'>
              <th>OP</th>
              <th>First Name</th>
              <th>Last Name</th>
              <th>Date of Birth</th>
              <th></th>
            </tr>
            {readerList.map(reader => (
              <tr className='table-light' key={reader.id}>
                <td>{reader.idCard}</td>
                <td>{reader.firstName}</td>
                <td>{reader.lastName}</td>
                <td>{reader.dateOfBirth.split("-").reverse().join(".")}</td>
                <td>
                  <div>
                    <button className='btn btn-outline-danger' style={{ marginLeft: '10px' }} onClick={() => editBtn(reader.id)}>Edit</button>
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