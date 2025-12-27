import { useEffect, useState } from "react";
import { get } from "../api/client.js";

export default function Profile() {
  const [profil, setProfil] = useState(null);
  const [error, setError] = useState("");

  useEffect(() => {
    let ignore = false;
    async function load() {
      try {
        const data = await get("/api/profil/profilim");
        if (!ignore) {
          setProfil(data);
        }
      } catch (err) {
        if (!ignore) {
          setError("Profil goruntulenemiyor. Giris yapmaniz gerekebilir.");
        }
      }
    }
    load();
    return () => {
      ignore = true;
    };
  }, []);

  return (
    <section className="page">
      <div className="section-header">
        <h1>Profil</h1>
      </div>
      {error && <div className="alert">{error}</div>}
      {profil && (
        <div className="card profile-card">
          <div className="profile-main">
            <div className="profile-avatar">
              {profil.resim ? (
                <img src={profil.resim} alt="profile" />
              ) : (
                <span>{profil.isim?.charAt(0) || "U"}</span>
              )}
            </div>
            <div>
              <div className="card-title">
                {profil.isim} {profil.soyisim}
              </div>
              <div className="card-meta">{profil.kullaniciAdi}</div>
              <div className="card-meta">{profil.kullaniciEposta}</div>
            </div>
          </div>
          <div className="card-body">{profil.kullaniciBiyografi}</div>
          <div className="card-row">
            <span>Ulke: {profil.ulke}</span>
            <span>Dogum tarihi: {profil.dogumTarihi}</span>
          </div>
        </div>
      )}
    </section>
  );
}
