import { useState, useEffect } from "react";
import { Link } from 'react-router-dom';

function Home() {
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
          <Link to={`/books/${b.id}`}>
            <h2>{b.name}</h2>
          </Link>
          <p>{b.author_name}</p>
        </div>
      ))}
    </>
  );
}

export default Home;
