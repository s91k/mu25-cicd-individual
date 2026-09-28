import { BrowserRouter, Routes, Route } from "react-router-dom";
import { useState } from "react";

import HomePage from "./pages/HomePage.jsx";
import BookPage from "./pages/BookPage.jsx";
import CartPage from "./pages/CartPage.jsx";

import "./App.css";

function App() {
  const [cart, setCart] = useState([]);

  const addToCart = (book) =>
    cart.every((c) => c.id != book.id) && setCart((cart) => [...cart, book]);

  const removeFromCart = (book) =>
    setCart((cart) => cart.filter((c) => c != book));

  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/books/:id" element={<BookPage addToCart={addToCart} />} />
        <Route
          path="/cart"
          element={<CartPage cart={cart} removeFromCart={removeFromCart} />}
        />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
