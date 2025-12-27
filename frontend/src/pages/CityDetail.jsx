import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import { get } from "../api/client.js";

export default function CityDetail() {
  const { id } = useParams();
  const [sehir, setSehir] = useState(null);
  const [gezilecekYerler, setGezilecekYerler] = useState([]);
  const [etkinlikler, setEtkinlikler] = useState([]);
  const [oteller, setOteller] = useState([]);
  const [error, setError] = useState("");

  useEffect(() => {
    let ignore = false;

    async function load() {
      try {
        const city = await get(`/api/sehirler/${id}`);
        const places = await get(`/api/gezilecekyerler/sehir/${id}`);
        const events = await get(`/api/etkinlikler/sehir/${id}`);
        const hotels = await get(`/api/oteller/search-fn?sehir=${encodeURIComponent(city.adi)}`);
        if (!ignore) {
          setSehir(city);
          setGezilecekYerler(Array.isArray(places) ? places : []);
          setEtkinlikler(Array.isArray(events) ? events : []);
          setOteller(Array.isArray(hotels) ? hotels : []);
        }
      } catch (err) {
        if (!ignore) {
          setError(err.message || "Sehir verisi yuklenemedi");
        }
      }
    }

    load();
    return () => {
      ignore = true;
    };
  }, [id]);

  return (
    <section className="page">
      <div className="section-header">
        <h1>{sehir ? sehir.adi : "Sehir"}</h1>
      </div>
      {error && <div className="alert">{error}</div>}

      <div className="section">
        <div className="section-header">
          <h2>Gezilecek yerler</h2>
        </div>
        <div className="grid">
          {gezilecekYerler.map((yer) => (
            <div key={yer.id} className="card">
              <div className="card-title">{yer.yerAdi}</div>
              <div className="card-body">{yer.yerIcerigi}</div>
            </div>
          ))}
        </div>
      </div>

      <div className="section">
        <div className="section-header">
          <h2>Etkinlikler</h2>
        </div>
        <div className="grid">
          {etkinlikler.map((etkinlik) => (
            <div key={etkinlik.etkinlikId} className="card">
              <div className="card-title">{etkinlik.etkinlikAdi}</div>
              <div className="card-meta">{etkinlik.etkinlikTuru}</div>
              <div className="card-body">{etkinlik.icerigi}</div>
              <div className="card-meta">{etkinlik.tarihi}</div>
            </div>
          ))}
        </div>
      </div>

      <div className="section">
        <div className="section-header">
          <h2>Sehirdeki oteller</h2>
        </div>
        <div className="grid">
          {oteller.map((otel) => (
            <Link key={otel.otelId} className="card" to={`/otel/${otel.otelId}`}>
              <div className="card-title">{otel.otelAdi}</div>
              <div className="card-meta">Puan: {otel.ortalamaPuan ?? "-"}</div>
            </Link>
          ))}
        </div>
      </div>
    </section>
  );
}
