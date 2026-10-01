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
      <h1>Order</h1>
      <p>Ordernummer: {order.id}</p>
      <p>E-post: {order.email}</p>
      <h2>Leveransadress</h2>
      <p>
        {order.firstName} {order.lastName}
      </p>
      <p>{order.streetAddress}</p>
      <p>
        {order.postalCode} {order.city}
      </p>
      <h2>Beställda varor:</h2>
      <ul>
        {order.orderItems.map((item) => (
          <li>
            {item.quantity} x {item.bookName}
          </li>
        ))}
      </ul>
    </>
  );
}

export default BookPage;
