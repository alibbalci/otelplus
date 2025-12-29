import { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import HotelService from '../../services/hotel.service';
import SehirService from '../../services/city.service';
import { Save, ArrowLeft } from 'lucide-react';

const AdminHotelForm = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const isEditMode = !!id;

    const [formData, setFormData] = useState({
        otelAdi: '',
        adres: '',
        telefon: '',
        sehir: ''
    });
    const [cities, setCities] = useState([]);
    const [loading, setLoading] = useState(false);
    const [pageLoading, setPageLoading] = useState(isEditMode);

    useEffect(() => {
        loadCities();
        if (isEditMode) {
            loadHotelData(id);
        }
    }, [id]);

    const loadCities = async () => {
        try {
            const data = await SehirService.getAllCities();
            setCities(data.data);
        } catch (error) {
            console.error('Error loading cities:', error);
        }
    };

    const loadHotelData = async (hotelId) => {
        try {
            const response = await HotelService.getHotelById(hotelId);
            const hotel = response.data;
            setFormData({
                otelAdi: hotel.otelAdi,
                adres: hotel.adres || '',
                telefon: '', // Assuming API doesn't return phone in detail DTO or handled separately
                sehir: hotel.sehir
            });
        } catch (error) {
            console.error('Error loading hotel:', error);
            alert('Otel bilgileri yüklenemedi.');
            navigate('/admin/oteller');
        } finally {
            setPageLoading(false);
        }
    };

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);

        try {
            if (isEditMode) {
                await HotelService.updateHotel(id, formData);
                alert('Otel başarıyla güncellendi.');
            } else {
                await HotelService.addHotel(formData);
                alert('Yeni otel başarıyla eklendi.');
            }
            navigate('/admin/oteller');
        } catch (error) {
            console.error('Error saving hotel:', error);
            alert('Kaydetme işlemi sırasında bir hata oluştu.');
        } finally {
            setLoading(false);
        }
    };

    if (pageLoading) return <div>Yükleniyor...</div>;

    return (
        <div className="max-w-2xl mx-auto">
            <button
                onClick={() => navigate('/admin/oteller')}
                className="flex items-center text-gray-600 hover:text-gray-900 mb-6"
            >
                <ArrowLeft className="w-5 h-5 mr-1" />
                Listeye Dön
            </button>

            <div className="bg-white rounded-xl shadow-sm border border-gray-100 p-8">
                <h2 className="text-2xl font-bold text-gray-800 mb-6">
                    {isEditMode ? 'Oteli Düzenle' : 'Yeni Otel Ekle'}
                </h2>

                <form onSubmit={handleSubmit} className="space-y-6">
                    <div>
                        <label className="block text-sm font-medium text-gray-700 mb-1">Otel Adı</label>
                        <input
                            type="text"
                            name="otelAdi"
                            value={formData.otelAdi}
                            onChange={handleChange}
                            required
                            className="w-full p-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:outline-none"
                        />
                    </div>

                    <div>
                        <label className="block text-sm font-medium text-gray-700 mb-1">Şehir</label>
                        <select
                            name="sehir"
                            value={formData.sehir}
                            onChange={handleChange}
                            required
                            className="w-full p-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:outline-none"
                        >
                            <option value="">Şehir Seçiniz</option>
                            {cities.map((city) => (
                                <option key={city.id} value={city.adi}>
                                    {city.adi}
                                </option>
                            ))}
                        </select>
                    </div>

                    <div>
                        <label className="block text-sm font-medium text-gray-700 mb-1">Adres</label>
                        <textarea
                            name="adres"
                            value={formData.adres}
                            onChange={handleChange}
                            rows="3"
                            className="w-full p-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:outline-none"
                        ></textarea>
                    </div>

                    {/* Telefon alanı opsiyonel eklenebilir */}

                    <div className="flex justify-end pt-4">
                        <button
                            type="submit"
                            disabled={loading}
                            className="bg-blue-600 text-white px-6 py-2 rounded-lg hover:bg-blue-700 transition flex items-center disabled:opacity-50"
                        >
                            <Save className="w-5 h-5 mr-2" />
                            {loading ? 'Kaydediliyor...' : 'Kaydet'}
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default AdminHotelForm;
