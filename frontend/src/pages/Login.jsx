import { useState } from "react";
import { post } from "../api/client.js";
import { useNavigate } from "react-router-dom";

export default function Login() {
  const navigate = useNavigate();
  const [form, setForm] = useState({ kullaniciAdi: "", kullaniciSifre: "" });
  const [error, setError] = useState("");

  async function handleSubmit(event) {
    event.preventDefault();
    setError("");
    try {
      const response = await post("/api/auth/login", form);
      localStorage.setItem("otelplus_token", response.token || "");
      localStorage.setItem("otelplus_user", response.kullaniciAdi || "");
      if (response.kullaniciId) {
        localStorage.setItem("otelplus_user_id", String(response.kullaniciId));
      }
      navigate("/");
    } catch (err) {
      setError(err.message || "Giris basarisiz");
    }
  }

  return (
    <section className="page form-page">
      <div className="card form-card">
        <h1>Giris</h1>
        {error && <div className="alert">{error}</div>}
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
            <label>Sifre</label>
            <input
              type="password"
              value={form.kullaniciSifre}
              onChange={(event) => setForm({ ...form, kullaniciSifre: event.target.value })}
              required
            />
          </div>
          <button className="button" type="submit">
            Giris yap
          </button>
        </form>
      </div>
    </section>
  );
}
