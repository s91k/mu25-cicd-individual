import { BrowserRouter, Routes, Route, Link, useParams } from 'react-router-dom';

import Home from "./Home.jsx";
import Book from "./Book.jsx";
import "./App.css";

function App() {

  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home/>}/>
        <Route path="/books/:id" element={<Book/>}/>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
