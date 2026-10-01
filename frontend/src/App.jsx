import { BrowserRouter, Routes, Route } from "react-router-dom";
import { useState } from "react";

import HomePage from "./pages/HomePage.jsx";
import BookPage from "./pages/BookPage.jsx";
import CartPage from "./pages/CartPage.jsx";
import CheckoutPage from "./pages/CheckoutPage.jsx";
import OrderPage from "./pages/OrderPage.jsx";

import "./App.css";

function App() {
  const [cart, setCart] = useState({});

  const addToCart = (book) =>
    setCart((prevCart) => ({
      ...prevCart,
      [book.id]: {
        ...book,
        quantity: (prevCart[book.id]?.quantity || 0) + 1,
      },
    }));

  const removeFromCart = (book) =>
    setCart((cart) => {
      delete cart[book.id];
      return cart;
    });

  const clearCart = () => setCart({});

  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/books/:id" element={<BookPage addToCart={addToCart} />} />
        <Route
          path="/cart"
          element={<CartPage cart={cart} removeFromCart={removeFromCart} />}
        />
        <Route
          path="/checkout"
          element={<CheckoutPage cart={cart} clearCart={clearCart} />}
        />
        <Route path="/orders/:id" element={<OrderPage />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
