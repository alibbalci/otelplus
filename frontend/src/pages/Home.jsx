import { useEffect, useMemo, useState } from "react";
import { Link } from "react-router-dom";
import { get } from "../api/client.js";

const sortOptions = [
  { value: "puan_desc", label: "Puana gore azalan" },
  { value: "puan_asc", label: "Puana gore artan" },
  { value: "fiyat_asc", label: "Fiyata gore artan" },
  { value: "fiyat_desc", label: "Fiyata gore azalan" },
];

export default function Home() {
  const [sehirler, setSehirler] = useState([]);
  const [oteller, setOteller] = useState([]);
  const [loading, setLoading] = useState(true);
  const [search, setSearch] = useState("");
  const [selectedCity, setSelectedCity] = useState("");
  const [sort, setSort] = useState("puan_desc");
  const [error, setError] = useState("");

  const query = useMemo(() => {
    const params = new URLSearchParams();
    if (search.trim()) {
      params.set("sehir", search.trim());
    }
    if (sort) {
      params.set("sort", sort);
    }
    return params.toString();
  }, [search, sort]);

  useEffect(() => {
    let ignore = false;

    async function load() {
      try {
        setLoading(true);
        setError("");
        const [cities, hotels] = await Promise.all([
          get("/api/sehirler"),
          get(`/api/oteller/search-fn?${query}`),
        ]);
        if (ignore) {
          return;
        }
        setSehirler(Array.isArray(cities) ? cities : []);
        setOteller(Array.isArray(hotels) ? hotels : []);
      } catch (err) {
        if (!ignore) {
          setError(err.message || "Veriler yuklenemedi");
        }
      } finally {
        if (!ignore) {
          setLoading(false);
        }
      }
    }

    load();
    return () => {
      ignore = true;
    };
  }, [query]);

  return (
    <section className="page">
      <div className="hero">
        <div>
          <h1>Konaklamani guvenle planla.</h1>
          <p>
            Otelleri kesfet, odalari gor ve rezervasyonunu tek yerden yap.
          </p>
        </div>
        <div className="hero-panel">
          <div className="field">
            <label>Sehir</label>
            <select
              value={selectedCity}
              onChange={(event) => {
                const value = event.target.value;
                setSelectedCity(value);
                setSearch(value);
              }}
            >
              <option value="">Tum sehirler</option>
              {sehirler.map((sehir) => (
                <option key={sehir.id} value={sehir.adi}>
                  {sehir.adi}
                </option>
              ))}
            </select>
          </div>
          <div className="field">
            <label>Siralama</label>
            <select value={sort} onChange={(event) => setSort(event.target.value)}>
              {sortOptions.map((option) => (
                <option key={option.value} value={option.value}>
                  {option.label}
                </option>
              ))}
            </select>
          </div>
          <div className="helper">Secilen sehire gore backend aramasi yapilir.</div>
        </div>
      </div>

      {error && <div className="alert">{error}</div>}

      <div className="section">
        <div className="section-header">
          <h2>One cikan oteller</h2>
          <span className="pill">{oteller.length} otel</span>
        </div>
        {loading ? (
          <div className="skeleton">Oteller yukleniyor...</div>
        ) : (
          <div className="grid">
            {oteller.map((otel) => (
              <Link key={otel.otelId} className="card" to={`/otel/${otel.otelId}`}>
                <div className="card-title">{otel.otelAdi}</div>
                <div className="card-meta">Sehir: {otel.sehir}</div>
                <div className="card-row">
                  <span>Puan: {otel.ortalamaPuan ?? "-"}</span>
                  <span>Min fiyat: {otel.minFiyat ?? "-"}</span>
                </div>
              </Link>
            ))}
          </div>
        )}
      </div>

      <div className="section">
        <div className="section-header">
          <h2>Sehirler</h2>
          <span className="pill">{sehirler.length} sehir</span>
        </div>
        <div className="grid grid-compact">
          {sehirler.map((sehir) => (
            <Link key={sehir.id} className="card card-compact" to={`/sehir/${sehir.id}`}>
              <div className="card-title">{sehir.adi}</div>
              <div className="card-meta">Sehir rehberi</div>
            </Link>
          ))}
        </div>
      </div>
    </section>
  );
}
