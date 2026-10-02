import { useNavigate } from "react-router-dom";

function CheckoutPage(props) {
  const submitOrder = async (e) => {
    e.preventDefault();

    const form = e.currentTarget;
    const formValues = Object.fromEntries(new FormData(form));

    const requestBody = {
      ...formValues,
      orderItems: Object.values(props.cart).map((b) => ({
        bookId: b.id,
        quantity: b.quantity,
      })),
    };

    await fetch(`${import.meta.env.VITE_API_URL}/orders`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(requestBody),
    })
      .then((response) => response.json())
      .then((data) => {
        props.clearCart();
        navigate(`/orders/${data.id}`);
      });
  };

  const navigate = useNavigate();

  if (props.cart.length == 0) {
    return (
      <>
        <title>Kassa</title>
        <p>Error: Kundvagnen är tom.</p>
      </>
    );
  }

  return (
    <>
      <title>Kassa</title>
      <h2>Kassa</h2>
      <form class="checkout-form" onSubmit={submitOrder}>
        <label htmlFor="email">E-post:</label>
        <input id="email" name="email" type="email" required />
        <label htmlFor="firstName">Förnamn:</label>
        <input id="firstName" name="firstName" required />
        <label htmlFor="lastName">Efternamn:</label>
        <input id="lastName" name="lastName" required />
        <label htmlFor="streetAddress">Gatuadress:</label>
        <input id="streetAddress" name="streetAddress" required />
        <label htmlFor="postalCode">Postnummer:</label>
        <input id="postalCode" name="postalCode" required />
        <label htmlFor="city">Stad:</label>
        <input id="city" name="city" required />
        <button id="submit" type="submit">
          Beställ
        </button>
      </form>
    </>
  );
}

export default CheckoutPage;
