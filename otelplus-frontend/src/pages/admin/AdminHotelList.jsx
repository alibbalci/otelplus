import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import HotelService from '../../services/hotel.service';
import { Plus, Edit, Trash2, MapPin, Bed } from 'lucide-react';

const AdminHotelList = () => {
    const [hotels, setHotels] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        fetchHotels();
    }, []);

    const fetchHotels = async () => {
        try {
            const data = await HotelService.getAllHotels();
            setHotels(data.data);
        } catch (error) {
            console.error('Error fetching hotels:', error);
        } finally {
            setLoading(false);
        }
    };

    const handleDelete = async (id) => {
        if (window.confirm('Bu oteli silmek istediğinize emin misiniz?')) {
            try {
                await HotelService.deleteHotel(id);
                setHotels(hotels.filter(hotel => hotel.otelId !== id));
            } catch (error) {
                console.error('Error deleting hotel:', error);
                alert('Otel silinirken bir hata oluştu.');
            }
        }
    };

    if (loading) return <div className="p-4">Yükleniyor...</div>;

    return (
        <div>
            <div className="flex justify-between items-center mb-6">
                <h2 className="text-2xl font-bold text-gray-800">Otel Listesi</h2>
                <Link
                    to="/admin/oteller/yeni"
                    className="flex items-center bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 transition"
                >
                    <Plus className="w-5 h-5 mr-2" />
                    Yeni Otel Ekle
                </Link>
            </div>

            <div className="bg-white rounded-xl shadow-sm border border-gray-100 overflow-hidden">
                <table className="w-full text-left border-collapse">
                    <thead>
                        <tr className="bg-gray-50 border-b border-gray-200 text-gray-700">
                            <th className="p-4 font-semibold">Otel Adı</th>
                            <th className="p-4 font-semibold">Şehir</th>
                            <th className="p-4 font-semibold">Puan</th>
                            <th className="p-4 font-semibold text-right">İşlemler</th>
                        </tr>
                    </thead>
                    <tbody className="divide-y divide-gray-100">
                        {hotels.map((hotel) => (
                            <tr key={hotel.otelId} className="hover:bg-gray-50 transition">
                                <td className="p-4 font-medium text-gray-900">{hotel.otelAdi}</td>
                                <td className="p-4 text-gray-600 flex items-center">
                                    <MapPin className="w-4 h-4 mr-1 text-gray-400" />
                                    {hotel.sehir}
                                </td>
                                <td className="p-4">
                                    <span className="bg-blue-100 text-blue-800 text-xs font-semibold px-2.5 py-0.5 rounded">
                                        {hotel.ortalamaPuan ? hotel.ortalamaPuan.toFixed(1) : '-'}
                                    </span>
                                </td>
                                <td className="p-4 text-right">
                                    <div className="flex space-x-2 justify-end">
                                        <Link
                                            to={`/admin/oteller/${hotel.otelId}/odalar`}
                                            className="p-2 text-blue-600 hover:bg-blue-50 rounded-lg transition"
                                            title="Odaları Yönet"
                                        >
                                            <Bed className="w-5 h-5" />
                                        </Link>
                                        <Link
                                            to={`/admin/oteller/duzenle/${hotel.otelId}`}
                                            className="p-2 text-indigo-600 hover:bg-indigo-50 rounded-lg transition"
                                            title="Düzenle"
                                        >
                                            <Edit className="w-5 h-5" />
                                        </Link>
                                        <button
                                            onClick={() => handleDelete(hotel.otelId)}
                                            className="p-2 text-red-600 hover:bg-red-50 rounded-lg transition"
                                            title="Sil"
                                        >
                                            <Trash2 className="w-5 h-5" />
                                        </button>
                                    </div>
                                </td>
                            </tr>
                        ))}
                        {hotels.length === 0 && (
                            <tr>
                                <td colSpan="4" className="p-8 text-center text-gray-500">
                                    Henüz kayıtlı otel yok.
                                </td>
                            </tr>
                        )}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default AdminHotelList;
