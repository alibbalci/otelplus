import { Routes, Route, NavLink } from "react-router-dom";
import Home from "./pages/Home.jsx";
import HotelDetail from "./pages/HotelDetail.jsx";
import Login from "./pages/Login.jsx";
import Register from "./pages/Register.jsx";
import Reservations from "./pages/Reservations.jsx";
import Profile from "./pages/Profile.jsx";
import CityDetail from "./pages/CityDetail.jsx";

export default function App() {
  return (
    <div className="app-shell">
      <header className="topbar">
        <div className="brand">
          <span className="brand-mark">OP</span>
          <div>
            <div className="brand-title">OtelPlus</div>
            <div className="brand-subtitle">Otel kesfi ve konaklama planlama</div>
          </div>
        </div>
        <nav className="nav">
          <NavLink to="/" end>
            Kesfet
          </NavLink>
          <NavLink to="/reservations">Rezervasyonlar</NavLink>
          <NavLink to="/profile">Profil</NavLink>
          <NavLink to="/login">Giris</NavLink>
          <NavLink to="/register">Kayit</NavLink>
        </nav>
      </header>

      <main className="content">
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/otel/:id" element={<HotelDetail />} />
          <Route path="/sehir/:id" element={<CityDetail />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/reservations" element={<Reservations />} />
          <Route path="/profile" element={<Profile />} />
        </Routes>
      </main>

      <footer className="footer">
        <span>OtelPlus Arayuz</span>
        <span className="footer-dot">•</span>
        <span>React + Vite</span>
      </footer>
    </div>
  );
}
