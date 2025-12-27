import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { get, post } from "../api/client.js";

export default function HotelDetail() {
  const { id } = useParams();
  const storedUser = localStorage.getItem("otelplus_user") || "";
  const storedUserId = localStorage.getItem("otelplus_user_id") || "";
  const [otel, setOtel] = useState(null);
  const [restoranlar, setRestoranlar] = useState([]);
  const [menusByRestoran, setMenusByRestoran] = useState({});
  const [yorumForm, setYorumForm] = useState({
    kullaniciAdi: storedUser,
    yorumIcerigi: "",
    puan: 5,
  });
  const [rezForm, setRezForm] = useState({
    odaId: "",
    kullaniciId: storedUserId ? Number(storedUserId) : "",
    girisTarihi: "",
    cikisTarihi: "",
  });
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    let ignore = false;
    async function load() {
      try {
        setLoading(true);
        const detail = await get(`/api/oteller/${id}`);
        const restoranList = await get(`/api/restoranlar/otel/${id}`);
        if (ignore) {
          return;
        }
        setOtel(detail);
        setRestoranlar(Array.isArray(restoranList) ? restoranList : []);
      } catch (err) {
        if (!ignore) {
          setError(err.message || "Otel yuklenemedi");
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
  }, [id]);

  async function handleYorumSubmit(event) {
    event.preventDefault();
    setError("");
    setMessage("");
    try {
      await post(`/api/yorumlar/otel/${id}/yorumekle`, {
        otelId: Number(id),
        kullaniciAdi: yorumForm.kullaniciAdi,
        yorumIcerigi: yorumForm.yorumIcerigi,
        puan: Number(yorumForm.puan),
      });
      const updated = await get(`/api/oteller/${id}`);
      setOtel(updated);
      setYorumForm({ kullaniciAdi: "", yorumIcerigi: "", puan: 5 });
      setMessage("Yorum eklendi.");
    } catch (err) {
      setError(err.message || "Yorum eklenemedi");
    }
  }

  async function handleRezervasyonSubmit(event) {
    event.preventDefault();
    setError("");
    setMessage("");
    if (!rezForm.kullaniciId) {
      setError("Rezervasyon icin once giris yapmalisiniz.");
      return;
    }
    try {
      await post("/api/rezervasyonlar/rezervasyon", {
        odaId: Number(rezForm.odaId),
        kullaniciId: Number(rezForm.kullaniciId),
        girisTarihi: rezForm.girisTarihi,
        cikisTarihi: rezForm.cikisTarihi,
      });
      setMessage("Rezervasyon olusturuldu.");
      setRezForm({ odaId: "", kullaniciId: "", girisTarihi: "", cikisTarihi: "" });
    } catch (err) {
      setError(err.message || "Rezervasyon basarisiz");
    }
  }

  async function loadMenu(restoranId) {
    try {
      const menu = await get(`/api/menuler/restoran/${restoranId}`);
      setMenusByRestoran((prev) => ({
        ...prev,
        [restoranId]: Array.isArray(menu) ? menu : [],
      }));
    } catch (err) {
      setError(err.message || "Menu yuklenemedi");
    }
  }

  if (loading) {
    return (
      <section className="page">
        <div className="skeleton">Otel detaylari yukleniyor...</div>
      </section>
    );
  }

  if (!otel) {
    return (
      <section className="page">
        <div className="alert">Otel bulunamadi.</div>
      </section>
    );
  }

  return (
    <section className="page">
      <div className="detail-hero">
        <div>
          <h1>{otel.otelAdi}</h1>
          <p>
            {otel.sehir} • {otel.adres}
          </p>
        </div>
        <div className="detail-meta">
          <span>Puan: {otel.ortalamaPuan ?? "-"}</span>
          <span>Oda sayisi: {otel.odalar?.length ?? 0}</span>
        </div>
      </div>

      {error && <div className="alert">{error}</div>}
      {message && <div className="notice">{message}</div>}

      <div className="section">
        <div className="section-header">
          <h2>Odalar</h2>
        </div>
        <div className="grid">
          {(otel.odalar || []).map((oda) => (
            <div key={oda.odaId} className="card">
              <div className="card-title">{oda.odaTipi}</div>
              <div className="card-meta">Kapasite: {oda.kapasite}</div>
              <div className="card-row">Fiyat: {oda.fiyat}</div>
            </div>
          ))}
        </div>
        <form className="form" onSubmit={handleRezervasyonSubmit}>
          <h3>Rezervasyon olustur</h3>
          <div className="form-grid">
            <select
              value={rezForm.odaId}
              onChange={(event) => setRezForm({ ...rezForm, odaId: event.target.value })}
              required
            >
              <option value="">Oda sec</option>
              {(otel.odalar || []).map((oda) => (
                <option key={oda.odaId} value={oda.odaId}>
                  {oda.odaTipi} (ID: {oda.odaId})
                </option>
              ))}
            </select>
            <input
              type="date"
              value={rezForm.girisTarihi}
              onChange={(event) => setRezForm({ ...rezForm, girisTarihi: event.target.value })}
              required
            />
            <input
              type="date"
              value={rezForm.cikisTarihi}
              onChange={(event) => setRezForm({ ...rezForm, cikisTarihi: event.target.value })}
              required
            />
          </div>
          <button className="button" type="submit">
            Rezervasyonu kaydet
          </button>
        </form>
      </div>

      <div className="section">
        <div className="section-header">
          <h2>Ozellikler</h2>
        </div>
        <div className="tag-list">
          {(otel.ozellikler || []).map((item, index) => (
            <span key={`${item}-${index}`} className="tag">
              {item}
            </span>
          ))}
        </div>
      </div>

      <div className="section">
        <div className="section-header">
          <h2>Restoranlar</h2>
        </div>
        <div className="grid">
          {restoranlar.map((restoran) => (
            <div key={restoran.id} className="card">
              <div className="card-title">{restoran.restoranAdi}</div>
              <div className="card-meta">Calisma saatleri: {restoran.calismaSaatleri}</div>
              <button className="button ghost" onClick={() => loadMenu(restoran.id)}>
                Menuyu yukle
              </button>
              {(menusByRestoran[restoran.id] || []).map((menu) => (
                <div key={menu.id} className="menu-item">
                  <span>{menu.urunAdi}</span>
                  <span className="menu-meta">{menu.ogunTuru}</span>
                </div>
              ))}
            </div>
          ))}
        </div>
      </div>

      <div className="section">
        <div className="section-header">
          <h2>Yorumlar</h2>
        </div>
        <div className="stack">
          {(otel.yorumlar || []).map((yorum) => (
            <div key={yorum.yorumId} className="card">
              <div className="card-title">{yorum.kullaniciAdi}</div>
              <div className="card-meta">Puan: {yorum.puan}</div>
              <div className="card-body">{yorum.yorumIcerigi}</div>
              <div className="card-meta">{yorum.yorumTarihi}</div>
            </div>
          ))}
        </div>
        <form className="form" onSubmit={handleYorumSubmit}>
          <h3>Yorum ekle</h3>
          <div className="form-grid">
            <input
              type="text"
              placeholder="Kullanici adi"
              value={yorumForm.kullaniciAdi}
              onChange={(event) =>
                setYorumForm({ ...yorumForm, kullaniciAdi: event.target.value })
              }
              readOnly={Boolean(storedUser)}
              required
            />
            <input
              type="number"
              min="1"
              max="5"
              value={yorumForm.puan}
              onChange={(event) => setYorumForm({ ...yorumForm, puan: event.target.value })}
              required
            />
          </div>
          <textarea
            rows="3"
            placeholder="Yorumunuz"
            value={yorumForm.yorumIcerigi}
            onChange={(event) =>
              setYorumForm({ ...yorumForm, yorumIcerigi: event.target.value })
            }
            required
          />
          <button className="button" type="submit">
            Yorumu gonder
          </button>
        </form>
      </div>
    </section>
  );
}
