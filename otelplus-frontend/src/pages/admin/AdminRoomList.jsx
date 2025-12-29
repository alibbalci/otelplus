import { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { Trash2, Plus, ArrowLeft } from 'lucide-react';
import HotelService from '../../services/hotel.service';

const AdminRoomList = () => {
    const { otelId } = useParams();
    const navigate = useNavigate();
    const [rooms, setRooms] = useState([]);
    const [loading, setLoading] = useState(true);
    const [showForm, setShowForm] = useState(false);

    // Form state
    const [formData, setFormData] = useState({
        odaTipi: '',
        kapasite: 1,
        fiyat: ''
    });

    useEffect(() => {
        fetchRooms();
    }, [otelId]);

    const fetchRooms = async () => {
        try {
            const response = await HotelService.getRooms(otelId);
            setRooms(response.data);
        } catch (error) {
            console.error("Odalar çekilemedi:", error);
        } finally {
            setLoading(false);
        }
    };

    const handleDelete = async (odaId) => {
        if (window.confirm('Bu odayı silmek istediğinize emin misiniz?')) {
            try {
                await HotelService.deleteRoom(odaId);
                fetchRooms(); // Refresh list
            } catch (error) {
                console.error("Oda silinemedi:", error);
                alert("Oda silinirken bir hata oluştu.");
            }
        }
    };

    const handleAddRoom = async (e) => {
        e.preventDefault();
        try {
            await HotelService.addRoom(otelId, formData);
            setShowForm(false);
            setFormData({ odaTipi: '', kapasite: 1, fiyat: '' });
            fetchRooms();
        } catch (error) {
            console.error("Oda eklenemedi:", error);
            alert("Oda eklenirken bir hata oluştu.");
        }
    };

    return (
        <div>
            <div className="flex items-center mb-6">
                <button
                    onClick={() => navigate('/admin/oteller')}
                    className="mr-4 p-2 text-gray-500 hover:text-gray-900 transition"
                >
                    <ArrowLeft className="w-6 h-6" />
                </button>
                <h2 className="text-2xl font-bold text-gray-800">Oda Yönetimi</h2>
            </div>

            {/* Add Room Form */}
            {showForm ? (
                <div className="bg-white p-6 rounded-xl shadow-sm border border-gray-100 mb-8">
                    <h3 className="text-lg font-bold mb-4">Yeni Oda Ekle</h3>
                    <form onSubmit={handleAddRoom} className="space-y-4">
                        <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
                            <div>
                                <label className="block text-sm font-medium text-gray-700">Oda Tipi</label>
                                <input
                                    type="text"
                                    required
                                    className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm p-2"
                                    value={formData.odaTipi}
                                    onChange={(e) => setFormData({ ...formData, odaTipi: e.target.value })}
                                    placeholder="Örn: Deluxe, Suite"
                                />
                            </div>
                            <div>
                                <label className="block text-sm font-medium text-gray-700">Kapasite</label>
                                <input
                                    type="number"
                                    required
                                    min="1"
                                    className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm p-2"
                                    value={formData.kapasite}
                                    onChange={(e) => setFormData({ ...formData, kapasite: e.target.value })}
                                />
                            </div>
                            <div>
                                <label className="block text-sm font-medium text-gray-700">Fiyat (TL)</label>
                                <input
                                    type="number"
                                    required
                                    min="0"
                                    className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm p-2"
                                    value={formData.fiyat}
                                    onChange={(e) => setFormData({ ...formData, fiyat: e.target.value })}
                                />
                            </div>
                        </div>
                        <div className="flex justify-end space-x-3">
                            <button
                                type="button"
                                onClick={() => setShowForm(false)}
                                className="px-4 py-2 text-gray-700 bg-gray-100 rounded-lg hover:bg-gray-200"
                            >
                                İptal
                            </button>
                            <button
                                type="submit"
                                className="px-4 py-2 text-white bg-blue-600 rounded-lg hover:bg-blue-700"
                            >
                                Kaydet
                            </button>
                        </div>
                    </form>
                </div>
            ) : (
                <div className="mb-6 flex justify-end">
                    <button
                        onClick={() => setShowForm(true)}
                        className="flex items-center px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition"
                    >
                        <Plus className="w-5 h-5 mr-2" />
                        Yeni Oda Ekle
                    </button>
                </div>
            )}

            {/* Rooms List */}
            <div className="bg-white rounded-xl shadow-sm border border-gray-100 overflow-hidden">
                <table className="w-full text-left text-sm text-gray-700">
                    <thead className="bg-gray-50 text-gray-900 font-semibold">
                        <tr>
                            <th className="p-4">Oda ID</th>
                            <th className="p-4">Oda Tipi</th>
                            <th className="p-4">Kapasite</th>
                            <th className="p-4">Fiyat</th>
                            <th className="p-4 text-right">İşlemler</th>
                        </tr>
                    </thead>
                    <tbody className="divide-y divide-gray-100">
                        {rooms.length > 0 ? (
                            rooms.map((room) => (
                                <tr key={room.odaId} className="hover:bg-gray-50">
                                    <td className="p-4">#{room.odaId}</td>
                                    <td className="p-4 font-medium text-gray-900">{room.odaTipi}</td>
                                    <td className="p-4">{room.kapasite} Kişilik</td>
                                    <td className="p-4 font-bold text-green-600">
                                        ₺{room.fiyat?.toLocaleString('tr-TR')}
                                    </td>
                                    <td className="p-4 text-right">
                                        <button
                                            onClick={() => handleDelete(room.odaId)}
                                            className="text-red-600 hover:text-red-900 transition"
                                            title="Sil"
                                        >
                                            <Trash2 className="w-5 h-5" />
                                        </button>
                                    </td>
                                </tr>
                            ))
                        ) : (
                            <tr>
                                <td colSpan="5" className="p-8 text-center text-gray-500">
                                    Bu otele ait henüz oda eklenmemiş.
                                </td>
                            </tr>
                        )}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default AdminRoomList;
