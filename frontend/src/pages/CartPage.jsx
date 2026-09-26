function CartPage(props) {
  return (
    <>
      <h1>Kundvagn</h1>
      {props.cart.length == 0 && <p>Kundvagnen är tom.</p>}
      {props.cart.map((b) => (
        <div key={b.id}>
          <h2>{b.name}</h2>
          <p>{b.author_name}</p>
          <button onClick={() => props.removeFromCart(b)}>Ta bort</button>
        </div>
      ))}
    </>
  );
}

export default CartPage;
