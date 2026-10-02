import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";

function BookPage(props) {
  const { id } = useParams();
  const [order, setOrder] = useState();

  useEffect(() => {
    fetch(`${import.meta.env.VITE_API_URL}/orders/${id}`)
      .then((res) => res.json())
      .then((json) => setOrder(json));
  }, []);

  if (order == undefined) {
    return <p>Laddar...</p>;
  }

  return (
    <>
      <title>{order.id}</title>
      <h2>Order</h2>
      <p>Ordernummer: {order.id}</p>
      <p id="email">E-post: {order.email}</p>
      <h3>Leveransadress</h3>
      <p>
        {order.firstName} {order.lastName}
      </p>
      <p>{order.streetAddress}</p>
      <p>
        {order.postalCode} {order.city}
      </p>
      <h3>Beställda varor</h3>
      <ul>
        {order.orderItems.map((item) => (
          <li key={item.bookId}>
            {item.quantity} x {item.bookName}
          </li>
        ))}
      </ul>
    </>
  );
}

export default BookPage;
