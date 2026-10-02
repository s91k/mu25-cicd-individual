import { useState, useEffect } from "react";
import { Link } from "react-router-dom";

function HomePage() {
  const [books, setBooks] = useState([]);

  useEffect(() => {
    fetch(`${import.meta.env.VITE_API_URL}/books`)
      .then((res) => res.json())
      .then((json) => setBooks(json));
  }, []);

  return (
    <section className="bookList">
      {books.map((b) => (
        <article key={b.id} className="bookItem">
          <Link to={`/books/${b.id}`}>
            <h2>{b.name}</h2>
          </Link>
          <p>{b.author_name}</p>
        </article>
      ))}
    </section>
  );
}

export default HomePage;
