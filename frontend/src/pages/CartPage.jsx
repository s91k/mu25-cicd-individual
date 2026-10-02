import { Link } from "react-router-dom";

function CartPage(props) {
  const cartItems = Object.values(props.cart);

  return (
    <>
      <title>Kundvagn</title>
      <h2>Kundvagn</h2>
      {cartItems.length == 0 && <p>Kundvagnen är tom.</p>}
      <section class="cart">
        {cartItems.map((b) => (
          <article key={b.id}>
            <div>
              <h3>{b.name}</h3>
              <p>{b.author_name}</p>
              <p>Antal: {b.quantity}</p>
            </div>
            <button
              className="remove-button"
              onClick={() => props.removeFromCart(b)}
            >
              Ta bort
            </button>
          </article>
        ))}
        {cartItems.length > 0 && <Link to="/checkout">Till kassan</Link>}
      </section>
    </>
  );
}

export default CartPage;
