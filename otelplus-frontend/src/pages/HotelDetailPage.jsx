import { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import HotelService from '../services/hotel.service';
import PlaceService from '../services/place.service';
import ActivityService from '../services/activity.service';
import RestaurantService from '../services/restaurant.service';
import CityService from '../services/city.service';
import CommentService from '../services/comment.service';
import ReservationService from '../services/reservation.service'; // Added
import { useAuth } from '../context/AuthContext';
import { Star, MapPin, ArrowLeft, Wifi, Map, Calendar, Utensils, Info, X, CheckCircle } from 'lucide-react';

const HotelDetailPage = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const { currentUser } = useAuth();
    const [hotel, setHotel] = useState(null);
    const [places, setPlaces] = useState([]);
    const [events, setEvents] = useState([]);
    const [restaurants, setRestaurants] = useState([]);
    const [loading, setLoading] = useState(true);
    const [activeTab, setActiveTab] = useState('overview'); // overview, places, events, dining
    const [selectedMenu, setSelectedMenu] = useState(null);
    const [isMenuLoading, setIsMenuLoading] = useState(false);
    const [menuModalOpen, setMenuModalOpen] = useState(false);

    // Reservation State
    const [resModalOpen, setResModalOpen] = useState(false);
    const [selectedRoom, setSelectedRoom] = useState(null);
    const [checkInDate, setCheckInDate] = useState('');
    const [checkOutDate, setCheckOutDate] = useState('');
    const [extras, setExtras] = useState([]); // State for selected extras
    const [resLoading, setResLoading] = useState(false);

    // Comment State
    const [newComment, setNewComment] = useState('');
    const [newRating, setNewRating] = useState(5);
    const [submittingComment, setSubmittingComment] = useState(false);

    useEffect(() => {
        const fetchHotelDetails = async () => {
            try {
                // 1. Get Hotel Details
                const response = await HotelService.getHotelById(id);
                setHotel(response.data);
                const hotelData = response.data;

                // 2. Resolve City ID from City Name (since DTO doesn't have ID)
                if (hotelData.sehir) {
                    const cityRes = await CityService.getAllCities();
                    const city = cityRes.data.find(c => c.adi === hotelData.sehir);

                    if (city) {
                        // 3. Fetch Places and Events for this City
                        const placesRes = await PlaceService.getPlacesByCityId(city.id);
                        setPlaces(placesRes.data);

                        const eventsRes = await ActivityService.getEventsByCityId(city.id);
                        setEvents(eventsRes.data);
                    }
                }

                // 4. Fetch Restaurants for this Hotel
                const restRes = await RestaurantService.getRestaurantsByHotelId(id);
                setRestaurants(restRes.data);

            } catch (error) {
                console.error("Otel detayları yüklenemedi:", error);
            } finally {
                setLoading(false);
            }
        };

        if (id) {
            fetchHotelDetails();
        }
    }, [id]);

    const handleCommentSubmit = async (e) => {
        e.preventDefault();
        if (!currentUser) return;

        setSubmittingComment(true);
        try {
            const commentData = {
                otelId: Number(id),
                kullaniciId: currentUser.kullaniciId,
                yorumIcerigi: newComment,
                puan: newRating
            };

            await CommentService.addComment(id, commentData);

            // Refresh logic - ideally fetch only comments but for now re-fetch hotel details
            const response = await HotelService.getHotelById(id);
            setHotel(response.data);

            // Reset form
            setNewComment('');
            setNewRating(5);
            alert("Yorumunuz başarıyla eklendi!");
        } catch (error) {
            console.error("Yorum eklenirken hata:", error);
            alert("Yorum eklenirken bir hata oluştu.");
        } finally {
            setSubmittingComment(false);
        }
    };

    const handleReservationClick = (room) => {
        if (!currentUser) {
            alert("Rezervasyon yapmak için lütfen giriş yapın.");
            navigate('/login');
            return;
        }
        setSelectedRoom(room);
        setResModalOpen(true);
    };

    const submitReservation = async (e) => {
        e.preventDefault();
        if (!checkInDate || !checkOutDate) {
            alert("Lütfen giriş ve çıkış tarihlerini seçiniz.");
            return;
        }

        setResLoading(true);
        try {
            const reservationData = {
                odaId: selectedRoom.odaId,
                kullaniciId: currentUser.kullaniciId,
                girisTarihi: checkInDate,
                cikisTarihi: checkOutDate,
                ekstralar: extras // Include extras
            };

            await ReservationService.createReservation(reservationData);
            setResModalOpen(false);
            alert("Rezervasyonunuz başarıyla oluşturuldu! İyi tatiller dileriz. 🎉");
            // Reset dates and extras
            setCheckInDate('');
            setCheckOutDate('');
            setExtras([]);
        } catch (error) {
            console.error("Rezervasyon hatası:", error);
            const errorMessage = error.response?.data?.message || error.response?.data || error.message || "Bilinmeyen bir hata oluştu";
            alert("Rezervasyon Hatası: " + errorMessage);
        } finally {
            setResLoading(false);
        }
    };

    const handleViewMenu = async (restaurantId) => {
        setIsMenuLoading(true);
        setMenuModalOpen(true);
        setSelectedMenu(null); // Clear previous
        try {
            const response = await RestaurantService.getMenuByRestaurantId(restaurantId);
            setSelectedMenu(response.data);
        } catch (error) {
            console.error("Menü yüklenemedi:", error);
        } finally {
            setIsMenuLoading(false);
        }
    };

    const closeMenuModal = () => {
        setMenuModalOpen(false);
        setSelectedMenu(null);
    };

    if (loading) {
        return (
            <div className="flex justify-center items-center h-screen">
                <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600"></div>
            </div>
        );
    }

    if (!hotel) {
        return (
            <div className="container mx-auto px-4 py-8 text-center">
                <h2 className="text-2xl font-bold text-gray-700">Otel bulunamadı.</h2>
                <button
                    onClick={() => navigate('/')}
                    className="mt-4 text-blue-600 hover:underline"
                >
                    Anasayfaya Dön
                </button>
            </div>
        );
    }

    return (
        <div className="container mx-auto px-4 py-8">
            <button
                onClick={() => navigate('/')}
                className="flex items-center text-gray-600 hover:text-blue-600 mb-6 transition"
            >
                <ArrowLeft className="w-5 h-5 mr-2" />
                Listeye Dön
            </button>

            {/* Header Section */}
            <div className="bg-white rounded-xl shadow-lg overflow-hidden mb-8">
                <div className="relative h-64 bg-gray-200">
                    <div className="absolute inset-0 flex items-center justify-center">
                        <span className="text-gray-400 text-xl font-medium">Otel Görseli</span>
                    </div>
                    <div className="absolute bottom-0 left-0 right-0 bg-gradient-to-t from-black/60 to-transparent p-6">
                        <h1 className="text-3xl font-bold text-white mb-2">{hotel.otelAdi}</h1>
                        <div className="flex items-center text-white/90">
                            <MapPin className="w-5 h-5 mr-2" />
                            <span className="text-lg">{hotel.sehir || hotel.adres}</span>
                        </div>
                    </div>
                </div>

                <div className="p-6">
                    <div className="flex flex-wrap items-center justify-between gap-4 mb-6">
                        <div className="flex items-center bg-yellow-100 px-3 py-1.5 rounded-lg">
                            <Star className="w-5 h-5 text-yellow-500 mr-2 fill-current" />
                            <span className="text-xl font-bold text-yellow-700">
                                {hotel.ortalamaPuan ? Number(hotel.ortalamaPuan).toFixed(1) : "0.0"}
                            </span>
                            <span className="text-yellow-600 ml-1 text-sm">(Ortalama Puan)</span>
                        </div>
                    </div>

                    <div className="flex border-b border-gray-200 mt-6">
                        <button
                            onClick={() => setActiveTab('overview')}
                            className={`pb-4 px-6 font-medium text-sm transition ${activeTab === 'overview' ? 'border-b-2 border-blue-600 text-blue-600' : 'text-gray-500 hover:text-gray-700'}`}
                        >
                            Genel Bakış
                        </button>
                        <button
                            onClick={() => setActiveTab('places')}
                            className={`pb-4 px-6 font-medium text-sm transition ${activeTab === 'places' ? 'border-b-2 border-blue-600 text-blue-600' : 'text-gray-500 hover:text-gray-700'}`}
                        >
                            Gezilecek Yerler
                        </button>
                        <button
                            onClick={() => setActiveTab('events')}
                            className={`pb-4 px-6 font-medium text-sm transition ${activeTab === 'events' ? 'border-b-2 border-blue-600 text-blue-600' : 'text-gray-500 hover:text-gray-700'}`}
                        >
                            Etkinlikler
                        </button>
                        <button
                            onClick={() => setActiveTab('dining')}
                            className={`pb-4 px-6 font-medium text-sm transition ${activeTab === 'dining' ? 'border-b-2 border-blue-600 text-blue-600' : 'text-gray-500 hover:text-gray-700'}`}
                        >
                            Restoranlar
                        </button>
                        <button
                            onClick={() => setActiveTab('reviews')}
                            className={`pb-4 px-6 font-medium text-sm transition ${activeTab === 'reviews' ? 'border-b-2 border-blue-600 text-blue-600' : 'text-gray-500 hover:text-gray-700'}`}
                        >
                            Yorumlar ({hotel.yorumlar ? hotel.yorumlar.length : 0})
                        </button>
                    </div>

                    <div className="mt-8">
                        {activeTab === 'overview' && (
                            <div className="prose max-w-none text-gray-600 animate-fadeIn">
                                <h3 className="text-xl font-semibold text-gray-800 mb-3">Otel Hakkında</h3>
                                <p>{hotel.aciklama || "Bu otel için henüz bir açıklama girilmemiş."}</p>

                                {/* Features Section */}
                                {hotel.ozellikler && hotel.ozellikler.length > 0 && (
                                    <div className="mt-8">
                                        <h3 className="text-xl font-semibold text-gray-800 mb-4">Otel Özellikleri</h3>
                                        <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
                                            {hotel.ozellikler.map((ozellik, index) => (
                                                <div key={index} className="flex items-center p-3 bg-gray-50 rounded-lg">
                                                    <div className="mr-3 text-blue-500">
                                                        <Wifi className="w-5 h-5" />
                                                    </div>
                                                    <span className="text-gray-700">{ozellik}</span>
                                                </div>
                                            ))}
                                        </div>
                                    </div>
                                )}
                            </div>
                        )}

                        {activeTab === 'places' && (
                            <div className="space-y-6 animate-fadeIn">
                                <h3 className="text-xl font-semibold text-gray-800 mb-4">Yakındaki Gezilecek Yerler</h3>
                                {places.length > 0 ? (
                                    <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                                        {places.map((place) => (
                                            <div key={place.yerId} className="bg-white border rounded-lg p-4 shadow-sm hover:shadow-md transition">
                                                <div className="flex items-start">
                                                    <div className="bg-blue-100 p-3 rounded-full mr-4">
                                                        <Map className="w-6 h-6 text-blue-600" />
                                                    </div>
                                                    <div>
                                                        <h4 className="font-bold text-gray-800 text-lg">{place.yerAdi}</h4>
                                                        <p className="text-gray-600 mt-1 line-clamp-2">{place.yerIcerigi}</p>
                                                    </div>
                                                </div>
                                            </div>
                                        ))}
                                    </div>
                                ) : (
                                    <p className="text-gray-500 italic">Bu şehirde kayıtlı gezilecek yer bulunamadı.</p>
                                )}
                            </div>
                        )}

                        {activeTab === 'events' && (
                            <div className="space-y-6 animate-fadeIn">
                                <h3 className="text-xl font-semibold text-gray-800 mb-4">Şehirdeki ve Oteldeki Etkinlikler</h3>
                                {events.length > 0 ? (
                                    <div className="space-y-4">
                                        {events.map((event) => (
                                            <div key={event.etkinlikId} className="flex bg-white border rounded-lg overflow-hidden shadow-sm">
                                                <div className="bg-blue-600 w-24 flex flex-col items-center justify-center text-white p-2">
                                                    <Calendar className="w-6 h-6 mb-1" />
                                                    <span className="text-xs text-center">{event.tarihi ? new Date(event.tarihi).toLocaleDateString('tr-TR') : 'Tarih Yok'}</span>
                                                </div>
                                                <div className="p-4 flex-1">
                                                    <div className="flex justify-between items-start">
                                                        <h4 className="font-bold text-gray-800 text-lg">{event.etkinlikAdi}</h4>
                                                        <span className={`text-xs px-2 py-1 rounded ${event.etkinlikTuru === 'otel_ici' ? 'bg-green-100 text-green-700' : 'bg-purple-100 text-purple-700'}`}>
                                                            {event.etkinlikTuru === 'otel_ici' ? 'Otel İçi' : 'Şehir İçi'}
                                                        </span>
                                                    </div>
                                                    <p className="text-gray-600 mt-1">{event.icerigi}</p>
                                                </div>
                                            </div>
                                        ))}
                                    </div>
                                ) : (
                                    <p className="text-gray-500 italic">Yaklaşan etkinlik bulunamadı.</p>
                                )}
                            </div>
                        )}

                        {activeTab === 'dining' && (
                            <div className="space-y-8 animate-fadeIn">
                                <h3 className="text-xl font-semibold text-gray-800 mb-4">Otelin Restoranları</h3>
                                {restaurants.length > 0 ? (
                                    <div className="grid grid-cols-1 gap-6">
                                        {restaurants.map((rest) => (
                                            <div key={rest.id} className="bg-white border rounded-lg p-6 shadow-sm">
                                                <div className="flex items-center mb-4">
                                                    <Utensils className="w-6 h-6 text-orange-500 mr-2" />
                                                    <div>
                                                        <h4 className="font-bold text-gray-800 text-xl">{rest.restoranAdi}</h4>
                                                        <span className="text-sm text-gray-500">{rest.calismaSaatleri || "Çalışma saatleri belirtilmemiş"}</span>
                                                    </div>
                                                </div>

                                                {/* Menu Preview */}
                                                <div className="mt-4">
                                                    <button
                                                        onClick={() => handleViewMenu(rest.id)}
                                                        className="text-blue-600 font-medium hover:underline text-sm flex items-center"
                                                    >
                                                        <Info className="w-4 h-4 mr-1" />
                                                        Menüyü Görüntüle
                                                    </button>
                                                </div>
                                            </div>
                                        ))}
                                    </div>
                                ) : (
                                    <p className="text-gray-500 italic">Bu otelde kayıtlı restoran bulunamadı.</p>
                                )}
                            </div>
                        )}

                        {activeTab === 'reviews' && (
                            <div className="space-y-8 animate-fadeIn">
                                {/* Comment Form */}
                                <div className="bg-gray-50 p-6 rounded-xl border border-gray-100">
                                    <h3 className="text-lg font-bold text-gray-800 mb-4">Yorum Yap</h3>
                                    {currentUser ? (
                                        <form onSubmit={handleCommentSubmit}>
                                            <div className="mb-4">
                                                <label className="block text-sm font-medium text-gray-700 mb-1">Puanınız</label>
                                                <div className="flex items-center space-x-1">
                                                    {[1, 2, 3, 4, 5].map((star) => (
                                                        <button
                                                            key={star}
                                                            type="button"
                                                            onClick={() => setNewRating(star)}
                                                            className="focus:outline-none"
                                                        >
                                                            <Star
                                                                className={`w-6 h-6 ${star <= newRating ? 'text-yellow-400 fill-current' : 'text-gray-300'}`}
                                                            />
                                                        </button>
                                                    ))}
                                                </div>
                                            </div>
                                            <div className="mb-4">
                                                <label className="block text-sm font-medium text-gray-700 mb-1">Yorumunuz</label>
                                                <textarea
                                                    className="w-full p-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:outline-none"
                                                    rows="3"
                                                    placeholder="Deneyiminizi paylaşın..."
                                                    value={newComment}
                                                    onChange={(e) => setNewComment(e.target.value)}
                                                    required
                                                ></textarea>
                                            </div>
                                            <button
                                                type="submit"
                                                disabled={submittingComment}
                                                className="bg-blue-600 text-white px-6 py-2 rounded-lg font-medium hover:bg-blue-700 transition disabled:opacity-50"
                                            >
                                                {submittingComment ? 'Gönderiliyor...' : 'Yorumu Gönder'}
                                            </button>
                                        </form>
                                    ) : (
                                        <div className="text-center py-4 text-gray-600">
                                            Yorum yapmak için lütfen <a href="/login" className="text-blue-600 font-medium hover:underline">giriş yapın</a>.
                                        </div>
                                    )}
                                </div>

                                {/* Review List */}
                                <div className="space-y-6">
                                    <h3 className="text-xl font-semibold text-gray-800">Misafir Yorumları</h3>
                                    {hotel.yorumlar && hotel.yorumlar.length > 0 ? (
                                        hotel.yorumlar.map((yorum) => (
                                            <div key={yorum.yorumId} className="border-b border-gray-100 last:border-0 pb-6 last:pb-0">
                                                <div className="flex justify-between items-start mb-2">
                                                    <div className="font-semibold text-gray-900">{yorum.kullaniciAdi || "Misafir"}</div>
                                                    <div className="flex items-center text-yellow-500">
                                                        <Star className="w-4 h-4 fill-current" />
                                                        <span className="ml-1 font-medium">{yorum.puan}</span>
                                                    </div>
                                                </div>
                                                <p className="text-gray-600">{yorum.yorumIcerigi}</p>
                                                <div className="mt-2 text-xs text-gray-400">
                                                    {new Date(yorum.yorumTarihi).toLocaleDateString('tr-TR')}
                                                </div>
                                            </div>
                                        ))
                                    ) : (
                                        <p className="text-gray-500 italic">Henüz yorum yapılmamış. İlk yorumu siz yapın!</p>
                                    )}
                                </div>
                            </div>
                        )}
                    </div>
                </div>
            </div>

            {/* Rooms Section */}
            {hotel.odalar && hotel.odalar.length > 0 && (
                <div className="mb-8">
                    <h2 className="text-2xl font-bold text-gray-800 mb-6">Odalar</h2>
                    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                        {hotel.odalar.map((oda) => (
                            <div key={oda.odaId} className="bg-white rounded-xl shadow border border-gray-100 overflow-hidden hover:shadow-md transition">
                                <div className="p-6">
                                    <h3 className="text-xl font-bold text-gray-800 mb-2">{oda.odaTipi}</h3>
                                    <div className="flex items-center text-gray-600 mb-4">
                                        <span className="text-sm">Kapasite: {oda.kapasite} Kişilik</span>
                                    </div>
                                    <div className="flex justify-between items-end mt-4">
                                        <div>
                                            <span className="text-2xl font-bold text-blue-600">{oda.fiyat} ₺</span>
                                            <span className="text-gray-500 text-sm"> / gece</span>
                                        </div>
                                        <button
                                            onClick={() => handleReservationClick(oda)}
                                            className="bg-green-600 text-white px-4 py-2 rounded-lg hover:bg-green-700 transition"
                                        >
                                            Rezervasyon Yap
                                        </button>
                                    </div>
                                </div>
                            </div>
                        ))}
                    </div>
                </div>
            )}


            {/* Menu Modal */}
            {menuModalOpen && (
                <div className="fixed inset-0 bg-black/50 z-50 flex items-center justify-center p-4">
                    <div className="bg-white rounded-xl shadow-2xl w-full max-w-2xl max-h-[80vh] overflow-hidden flex flex-col animate-fadeIn">
                        <div className="p-6 border-b border-gray-100 flex justify-between items-center bg-gray-50">
                            <h3 className="text-xl font-bold text-gray-800">Restoran Menüsü</h3>
                            <button onClick={closeMenuModal} className="text-gray-400 hover:text-gray-600 transition">
                                <X className="w-6 h-6" />
                            </button>
                        </div>

                        <div className="p-6 overflow-y-auto">
                            {isMenuLoading ? (
                                <div className="flex justify-center py-8">
                                    <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-blue-600"></div>
                                </div>
                            ) : selectedMenu && selectedMenu.length > 0 ? (
                                <div className="space-y-6">
                                    {selectedMenu.map((item) => (
                                        <div key={item.id} className="flex items-center bg-white border border-gray-100 rounded-lg p-3 shadow-sm hover:shadow-md transition">
                                            <div className="w-20 h-20 bg-gray-200 rounded-md overflow-hidden flex-shrink-0 mr-4">
                                                {item.resimYolu ? (
                                                    <img src={item.resimYolu} alt={item.urunAdi} className="w-full h-full object-cover" />
                                                ) : (
                                                    <div className="w-full h-full flex items-center justify-center text-gray-400 text-xs">Görsel Yok</div>
                                                )}
                                            </div>
                                            <div className="flex-1">
                                                <div className="flex justify-between items-start">
                                                    <h4 className="font-bold text-gray-800">{item.urunAdi}</h4>
                                                    <span className="text-xs bg-orange-100 text-orange-700 px-2 py-1 rounded-full">{item.ogunTuru}</span>
                                                </div>
                                            </div>
                                        </div>
                                    ))}
                                </div>
                            ) : (
                                <div className="text-center py-8 text-gray-500">
                                    Bu restoran için henüz menü eklenmemiş.
                                </div>
                            )}
                        </div>

                        <div className="p-4 border-t border-gray-100 bg-gray-50 text-right">
                            <button onClick={closeMenuModal} className="px-6 py-2 bg-gray-200 text-gray-700 rounded-lg hover:bg-gray-300 transition font-medium">
                                Kapat
                            </button>
                        </div>
                    </div>
                </div>
            )}

            {/* Reservation Modal */}
            {resModalOpen && selectedRoom && (
                <div className="fixed inset-0 bg-black/50 z-50 flex items-center justify-center p-4">
                    <div className="bg-white rounded-xl shadow-2xl w-full max-w-md overflow-hidden animate-fadeIn">
                        <div className="p-6 border-b border-gray-100 flex justify-between items-center bg-blue-600 text-white">
                            <h3 className="text-xl font-bold">Rezervasyon Oluştur</h3>
                            <button onClick={() => setResModalOpen(false)} className="text-blue-100 hover:text-white transition">
                                <X className="w-6 h-6" />
                            </button>
                        </div>

                        <form onSubmit={submitReservation} className="p-6">
                            <div className="mb-4 bg-blue-50 p-4 rounded-lg">
                                <h4 className="font-bold text-gray-800">{hotel.otelAdi}</h4>
                                <p className="text-gray-600 text-sm">{selectedRoom.odaTipi}</p>
                                <p className="text-blue-600 font-bold mt-1">{selectedRoom.fiyat} ₺ <span className="text-sm font-normal text-gray-500">/ gece</span></p>
                            </div>

                            <div className="space-y-4">
                                <div>
                                    <label className="block text-sm font-medium text-gray-700 mb-1">Giriş Tarihi</label>
                                    <input
                                        type="date"
                                        value={checkInDate}
                                        onChange={(e) => setCheckInDate(e.target.value)}
                                        className="w-full p-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none"
                                        required
                                    />
                                </div>
                                <div>
                                    <label className="block text-sm font-medium text-gray-700 mb-1">Çıkış Tarihi</label>
                                    <input
                                        type="date"
                                        value={checkOutDate}
                                        onChange={(e) => setCheckOutDate(e.target.value)}
                                        className="w-full p-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none"
                                        required
                                    />
                                </div>
                            </div>

                            {/* Extras Selection */}
                            <div className="mt-4 p-4 bg-gray-50 rounded-lg">
                                <h5 className="font-semibold text-gray-700 mb-2">Ekstra Hizmetler</h5>
                                <div className="space-y-2">
                                    <label className="flex items-center space-x-2 cursor-pointer">
                                        <input
                                            type="checkbox"
                                            value="KAHVALTI"
                                            checked={extras.includes('KAHVALTI')}
                                            onChange={(e) => {
                                                if (e.target.checked) {
                                                    setExtras([...extras, 'KAHVALTI']);
                                                } else {
                                                    setExtras(extras.filter(item => item !== 'KAHVALTI'));
                                                }
                                            }}
                                            className="w-4 h-4 text-blue-600 rounded bg-gray-100 border-gray-300 focus:ring-blue-500"
                                        />
                                        <span className="text-gray-700">Sabah Kahvaltısı (+250 ₺)</span>
                                    </label>
                                    <label className="flex items-center space-x-2 cursor-pointer">
                                        <input
                                            type="checkbox"
                                            value="SPA"
                                            checked={extras.includes('SPA')}
                                            onChange={(e) => {
                                                if (e.target.checked) {
                                                    setExtras([...extras, 'SPA']);
                                                } else {
                                                    setExtras(extras.filter(item => item !== 'SPA'));
                                                }
                                            }}
                                            className="w-4 h-4 text-blue-600 rounded bg-gray-100 border-gray-300 focus:ring-blue-500"
                                        />
                                        <span className="text-gray-700">SPA Paketi (+500 ₺)</span>
                                    </label>
                                </div>
                            </div>

                            <div className="mt-8 flex justify-end space-x-3">
                                <button
                                    type="button"
                                    onClick={() => setResModalOpen(false)}
                                    className="px-4 py-2 text-gray-600 hover:bg-gray-100 rounded-lg transition"
                                >
                                    İptal
                                </button>
                                <button
                                    type="submit"
                                    disabled={resLoading}
                                    className="px-6 py-2 bg-green-600 text-white rounded-lg hover:bg-green-700 transition flex items-center disabled:opacity-50"
                                >
                                    {resLoading ? 'İşleniyor...' : (
                                        <>
                                            <CheckCircle className="w-5 h-5 mr-2" />
                                            Onayla
                                        </>
                                    )}
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            )}
        </div>
    );
};

export default HotelDetailPage;
