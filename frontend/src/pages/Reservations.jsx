import { useEffect, useState } from "react";
import { del, get } from "../api/client.js";

export default function Reservations() {
  const storedUserId = localStorage.getItem("otelplus_user_id") || "";
  const [kullaniciId] = useState(storedUserId);
  const [rezervasyonlar, setRezervasyonlar] = useState([]);
  const [error, setError] = useState("");
  const [message, setMessage] = useState("");

  async function loadReservations() {
    setError("");
    setMessage("");
    if (!kullaniciId) {
      setError("Rezervasyonlari gormek icin giris yapmalisiniz.");
      return;
    }
    try {
      const data = await get(`/api/rezervasyonlar/kullanici/${kullaniciId}`);
      setRezervasyonlar(Array.isArray(data) ? data : []);
    } catch (err) {
      setError(err.message || "Rezervasyonlar yuklenemedi");
    }
  }

  async function handleDelete(rezervasyonId) {
    setError("");
    setMessage("");
    try {
      await del(`/api/rezervasyonlar/delete/${rezervasyonId}`);
      setMessage("Rezervasyon iptal edildi.");
      loadReservations();
    } catch (err) {
      setError(err.message || "Iptal islemi basarisiz");
    }
  }

  useEffect(() => {
    if (kullaniciId) {
      loadReservations();
    }
  }, [kullaniciId]);

  return (
    <section className="page">
      <div className="section-header">
        <h1>Rezervasyonlar</h1>
      </div>
      <div className="form inline-form">
        <button className="button" onClick={loadReservations}>
          Rezervasyonlari yenile
        </button>
      </div>
      {error && <div className="alert">{error}</div>}
      {message && <div className="notice">{message}</div>}
      <div className="stack">
        {rezervasyonlar.map((rez) => (
          <div key={rez.rezervasyonId} className="card">
            <div className="card-title">{rez.otelAdi}</div>
            <div className="card-meta">Oda: {rez.odaTipi}</div>
            <div className="card-row">
              <span>Giris: {rez.girisTarihi}</span>
              <span>Cikis: {rez.cikisTarihi}</span>
            </div>
            <div className="card-row">
              <span>Toplam: {rez.rezervasyonMaliyeti}</span>
              <button className="button ghost" onClick={() => handleDelete(rez.rezervasyonId)}>
                Iptal et
              </button>
            </div>
          </div>
        ))}
      </div>
    </section>
  );
}
