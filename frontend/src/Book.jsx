import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";

function Book() {
  const { id } = useParams();
  const [book, setBook] = useState();

  useEffect(() => {
    fetch(`${import.meta.env.VITE_API_URL}/books/${id}`)
      .then((res) => res.json())
      .then((json) => setBook(json));
  }, []);

  if(book == undefined){
    return (<p>Laddar...</p>);
  }

  return (
    <>
      <h1>{book.name}</h1>
      <p>{book.author_name}</p>
      <p>{book.releaseDate}</p>
      <p>{book.description}</p>
    </>
  );
}

export default Book;
