import { useState, useEffect } from 'react';
import ReportService from '../../services/report.service';

const AdminDashboard = () => {
    const [generalReport, setGeneralReport] = useState([]);
    const [cityReport, setCityReport] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        fetchReports();
    }, []);

    const fetchReports = async () => {
        try {
            setLoading(true);
            // Son 1 yılın verisi
            const endDate = new Date().toISOString().split('T')[0];
            const startDate = new Date(new Date().setFullYear(new Date().getFullYear() - 1)).toISOString().split('T')[0];

            const [genelResponse, sehirResponse] = await Promise.all([
                ReportService.getGeneralReport(startDate, endDate),
                ReportService.getCityReport(startDate, endDate)
            ]);

            setGeneralReport(Array.isArray(genelResponse.data) ? genelResponse.data : []);
            setCityReport(Array.isArray(sehirResponse.data) ? sehirResponse.data : []);

        } catch (err) {
            console.error("Rapor verisi çekilemedi:", err);
            setError("Raporlar yüklenirken bir hata oluştu. Backend bağlantısını kontrol edin.");
        } finally {
            setLoading(false);
        }
    };

    if (loading) return <div className="p-10 text-center">Veriler Yükleniyor...</div>;
    if (error) return <div className="p-10 text-center text-red-600 font-bold">{error}</div>;

    return (
        <div className="space-y-8">
            {/* 1. Genel Detaylı Rapor Tablosu */}
            <div className="bg-white rounded-xl shadow-sm border border-gray-100 overflow-hidden">
                <div className="p-6 border-b border-gray-100">
                    <h2 className="text-xl font-bold text-gray-800">Genel Detaylı Rapor (Son 1 Yıl)</h2>
                    <p className="text-sm text-gray-500">Fonksiyon: fn_tr_genel_detayli_rapor</p>
                </div>
                <div className="overflow-x-auto">
                    <table className="w-full text-left text-sm text-gray-700">
                        <thead className="bg-gray-50 text-gray-900 font-semibold">
                            <tr>
                                <th className="p-3 border-b">Sıra</th>
                                <th className="p-3 border-b">Şehir</th>
                                <th className="p-3 border-b">Otel Sayısı</th>
                                <th className="p-3 border-b">Oda Sayısı</th>
                                <th className="p-3 border-b">Rezerv.</th>
                                <th className="p-3 border-b">Toplam Ciro</th>
                                <th className="p-3 border-b">Ort. Rez. Tutar</th>
                                <th className="p-3 border-b">Ort. Gece</th>
                                <th className="p-3 border-b">Popüler Otel</th>
                                <th className="p-3 border-b">Popüler Oda Tipi</th>
                            </tr>
                        </thead>
                        <tbody className="divide-y divide-gray-100">
                            {generalReport.length > 0 ? (
                                generalReport.map((row, index) => (
                                    <tr key={index} className="hover:bg-gray-50">
                                        <td className="p-3 text-center">{row.sehirSiralama}</td>
                                        <td className="p-3 font-medium">{row.sehirAdi}</td>
                                        <td className="p-3 text-center">{row.toplamOtel}</td>
                                        <td className="p-3 text-center">{row.toplamOda}</td>
                                        <td className="p-3 text-center">{row.toplamRezervasyon}</td>
                                        <td className="p-3 text-green-600 font-bold">
                                            ₺{row.toplamCiro ? row.toplamCiro.toLocaleString('tr-TR', { minimumFractionDigits: 2 }) : '0'}
                                        </td>
                                        <td className="p-3">
                                            ₺{row.ortalamaRezTutar ? row.ortalamaRezTutar.toLocaleString('tr-TR', { minimumFractionDigits: 2 }) : '0'}
                                        </td>
                                        <td className="p-3 text-center">{row.ortalamaKalisGece}</td>
                                        <td className="p-3">{row.enCokTercihEdilenOtel || '-'}</td>
                                        <td className="p-3">{row.enCokTercihEdilenOdaTipi || '-'}</td>
                                    </tr>
                                ))
                            ) : (
                                <tr>
                                    <td colSpan="10" className="p-8 text-center text-gray-500">Veri bulunamadı.</td>
                                </tr>
                            )}
                        </tbody>
                    </table>
                </div>
            </div>

            {/* 2. Şehir Rezervasyon Özet Raporu */}
            <div className="bg-white rounded-xl shadow-sm border border-gray-100 overflow-hidden">
                <div className="p-6 border-b border-gray-100">
                    <h2 className="text-xl font-bold text-gray-800">Şehir Rezervasyon Özeti</h2>
                    <p className="text-sm text-gray-500">Fonksiyon: fn_sehir_rezervasyon_ozet_raporu</p>
                </div>
                <div className="overflow-x-auto">
                    <table className="w-full text-left text-sm text-gray-700">
                        <thead className="bg-gray-50 text-gray-900 font-semibold">
                            <tr>
                                <th className="p-4 border-b">Şehir</th>
                                <th className="p-4 border-b">Toplam Rez.</th>
                                <th className="p-4 border-b">Ciro</th>
                                <th className="p-4 border-b">Popüler Oda Tipi</th>
                                <th className="p-4 border-b">Oda Ort. Fiyat</th>
                            </tr>
                        </thead>
                        <tbody className="divide-y divide-gray-100">
                            {cityReport.length > 0 ? (
                                cityReport.map((row, index) => (
                                    <tr key={index} className="hover:bg-gray-50">
                                        <td className="p-4 font-medium">{row.sehirAdi}</td>
                                        <td className="p-4">{row.toplamRezervasyon}</td>
                                        <td className="p-4 text-blue-600">
                                            ₺{row.toplamCiro ? row.toplamCiro.toLocaleString('tr-TR', { minimumFractionDigits: 2 }) : '0'}
                                        </td>
                                        <td className="p-4">{row.enCokOdaTipi || '-'}</td>
                                        <td className="p-4">
                                            ₺{row.enCokOdaOrtalamaFiyat ? row.enCokOdaOrtalamaFiyat.toLocaleString('tr-TR') : '0'}
                                        </td>
                                    </tr>
                                ))
                            ) : (
                                <tr>
                                    <td colSpan="5" className="p-8 text-center text-gray-500">Veri bulunamadı.</td>
                                </tr>
                            )}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );
};

export default AdminDashboard;
