import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";

function BookPage(props) {
  const { id } = useParams();
  const [book, setBook] = useState();

  useEffect(() => {
    fetch(`${import.meta.env.VITE_API_URL}/books/${id}`)
      .then((res) => res.json())
      .then((json) => setBook(json));
  }, []);

  if (book == undefined) {
    return <p>Laddar...</p>;
  }

  return (
    <>
      <title>{book.name}</title>
      <h2>{book.name}</h2>
      <p>{book.author_name}</p>
      <button className="buyButton" onClick={() => props.addToCart(book)}>
        Lägg i kundvagn
      </button>
      <p>{book.releaseDate}</p>
      <p>{book.description}</p>
    </>
  );
}

export default BookPage;
