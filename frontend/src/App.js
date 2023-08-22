import AppBar from './components/AppBar';
import Books from './components/Books';
import Readers from './components/Readers';
import Loans from './components/Loans';
import '../node_modules/bootstrap/dist/css/bootstrap.min.css';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import './App.css';

function App() {
  return (
    <div className="container">

      <div>

        <Router>

          {<AppBar />}

          <Routes>
            <Route exact path='/books' element={<Books />} />
            <Route exact path='/readers' element={<Readers />} />
            <Route exact path='/loans' element={<Loans />} />
          </Routes>

        </Router>

      </div>

    </div>
  );
}

export default App;