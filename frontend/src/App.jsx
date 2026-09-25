import { useState, useEffect } from "react";
import "./App.css";

function App() {
  const [books, setBooks] = useState([]);

  useEffect(() => {
    fetch(`${import.meta.env.VITE_API_URL}/books`)
      .then((res) => res.json())
      .then((json) => setBooks(json));
  }, []);

  return (
    <>
      <h1>Bookstore</h1>
      {books.map((b) => (
        <div key={b.id}>
          <h2>{b.name}</h2>
        </div>
      ))}
    </>
  );
}

export default App;
