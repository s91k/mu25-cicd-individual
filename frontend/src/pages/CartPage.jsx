import { Link } from "react-router-dom";

function CartPage(props) {
  const cartItems = Object.values(props.cart);

  return (
    <>
      <title>Kundvagn</title>
      <h1>Kundvagn</h1>
      {cartItems.length == 0 && <p>Kundvagnen är tom.</p>}
      {cartItems.map((b) => (
        <div key={b.id}>
          <h2>{b.name}</h2>
          <p>{b.author_name}</p>
          <p>Antal: {b.quantity}</p>
          <button onClick={() => props.removeFromCart(b)}>Ta bort</button>
        </div>
      ))}
      {cartItems.length > 0 && <Link to="/checkout">Till kassan</Link>}
    </>
  );
}

export default CartPage;
