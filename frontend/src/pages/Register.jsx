import { useState } from "react";
import { post } from "../api/client.js";
import { useNavigate } from "react-router-dom";

export default function Register() {
  const navigate = useNavigate();
  const [form, setForm] = useState({
    kullaniciAdi: "",
    kullaniciEposta: "",
    kullaniciSifre: "",
    kullaniciCinsiyet: "",
  });
  const [error, setError] = useState("");
  const [message, setMessage] = useState("");

  async function handleSubmit(event) {
    event.preventDefault();
    setError("");
    setMessage("");
    try {
      await post("/api/auth/register", form);
      setMessage("Kayit basarili. Giris yapabilirsiniz.");
      setTimeout(() => navigate("/login"), 800);
    } catch (err) {
      setError(err.message || "Kayit basarisiz");
    }
  }

  return (
    <section className="page form-page">
      <div className="card form-card">
        <h1>Kayit</h1>
        {error && <div className="alert">{error}</div>}
        {message && <div className="notice">{message}</div>}
        <form onSubmit={handleSubmit}>
          <div className="field">
            <label>Kullanici adi</label>
            <input
              type="text"
              value={form.kullaniciAdi}
              onChange={(event) => setForm({ ...form, kullaniciAdi: event.target.value })}
              required
            />
          </div>
          <div className="field">
            <label>E-posta</label>
            <input
              type="email"
              value={form.kullaniciEposta}
              onChange={(event) => setForm({ ...form, kullaniciEposta: event.target.value })}
              required
            />
          </div>
          <div className="field">
            <label>Sifre</label>
            <input
              type="password"
              value={form.kullaniciSifre}
              onChange={(event) => setForm({ ...form, kullaniciSifre: event.target.value })}
              required
            />
          </div>
          <div className="field">
            <label>Cinsiyet</label>
            <input
              type="text"
              value={form.kullaniciCinsiyet}
              onChange={(event) =>
                setForm({ ...form, kullaniciCinsiyet: event.target.value })
              }
            />
          </div>
          <button className="button" type="submit">
            Hesap olustur
          </button>
        </form>
      </div>
    </section>
  );
}
