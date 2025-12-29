import { useEffect, useState } from 'react';
import HotelService from '../services/hotel.service';
import CityService from '../services/city.service'; // Import City Service
import { Star, MapPin, Search } from 'lucide-react'; // Added Calendar, Search

const HomePage = () => {
    const [hotels, setHotels] = useState([]);
    const [cities, setCities] = useState([]); // State for cities
    const [loading, setLoading] = useState(true);

    // Search States
    const [selectedCity, setSelectedCity] = useState('');
    const [sortOption, setSortOption] = useState('puan_azalan');

    useEffect(() => {
        loadInitialData();
    }, []);

    const loadInitialData = async () => {
        try {
            // Fetch only cities here. Hotels will be fetched by the useEffect dependent on selectedCity
            const cityRes = await CityService.getAllCities();
            setCities(cityRes.data);
        } catch (error) {
            console.error("Şehirler yüklenemedi:", error);
        }
    };

    // Automatic search when city or sort changes
    useEffect(() => {
        const fetchHotels = async () => {
            setLoading(true);
            try {
                let response;
                if (selectedCity) {
                    console.log("Backend Search -> City:", selectedCity, "Sort:", sortOption);
                    // Use the backend search endpoint
                    response = await HotelService.searchHotels(selectedCity, sortOption);
                } else {
                    response = await HotelService.getAllHotels();


                }

                console.log("Response Data:", response.data);
                setHotels(response.data);
            } catch (error) {
                console.error("Oteller yüklenemedi:", error);
            } finally {
                setLoading(false);
            }
        };

        fetchHotels();
    }, [selectedCity, sortOption]); // Re-run when city or sort options change

    // We can keep the manual search button as a "Refresh" or for Dates in future
    const handleSearch = (e) => {
        e.preventDefault();
        // The effect handles the fetch, but maybe we want to support date handling here later.
        // For now, triggering a re-fetch or just letting the user know.
        // Since effect runs on change, clicking search without changing city might need to re-fetch.
        // Let's extract the fetch logic.
    };

    return (
        <div className="container mx-auto px-4 py-8">
            <div className="mb-8 text-center">
                <h1 className="text-4xl font-bold text-gray-800 mb-4">Mükemmel Tatil Sizi Bekliyor</h1>
                <div className="bg-white p-6 rounded-xl shadow-lg max-w-4xl mx-auto">
                    <form onSubmit={handleSearch} className="grid grid-cols-1 md:grid-cols-3 gap-4 items-end">

                        {/* City Selection */}
                        <div className="flex flex-col">
                            <label className="text-sm font-semibold text-gray-700 mb-1 flex items-center">
                                <MapPin className="w-4 h-4 mr-1" /> Nereye?
                            </label>
                            <select
                                className="w-full p-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:outline-none bg-white"
                                value={selectedCity}
                                onChange={(e) => setSelectedCity(e.target.value)}
                            >
                                <option value="" disabled>Şehir Seçiniz</option>
                                {cities.map((city) => (
                                    <option key={city.id} value={city.adi}>
                                        {city.adi}
                                    </option>
                                ))}
                            </select>
                        </div>

                        {/* Sort Option */}
                        <div className="flex flex-col">
                            <label className="text-sm font-semibold text-gray-700 mb-1 flex items-center">
                                <Search className="w-4 h-4 mr-1" /> Sıralama
                            </label>
                            <select
                                className="w-full p-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:outline-none bg-white"
                                value={sortOption}
                                onChange={(e) => setSortOption(e.target.value)}
                            >
                                <option value="puan_azalan">Puan (Yüksekten Düşüğe)</option>
                                <option value="puan_artan">Puan (Düşükten Yükseğe)</option>
                            </select>
                        </div>

                        {/* Search Button */}
                        <button
                            type="submit"
                            className="w-full bg-blue-600 text-white font-bold py-3 px-6 rounded-lg hover:bg-blue-700 transition flex items-center justify-center"
                        >
                            <Search className="w-5 h-5 mr-2" />
                            Ara
                        </button>
                    </form>
                </div>
            </div>

            {loading ? (
                <div className="flex justify-center items-center h-64">
                    <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600"></div>
                </div>
            ) : hotels.length === 0 ? (
                <div className="text-center py-10">
                    <h2 className="text-xl text-gray-600">Bu kriterlere uygun otel bulunamadı.</h2>
                </div>
            ) : (
                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
                    {hotels.map((hotel) => (
                        <div key={hotel.id} className="bg-white rounded-xl shadow-lg overflow-hidden hover:shadow-xl transition duration-300">
                            {/* Hotel Image Placeholder - could be real if backend sends it */}
                            <div className="h-48 bg-gray-200 flex items-center justify-center">
                                <span className="text-gray-400">Otel Görseli</span>
                            </div>
                            <div className="p-6">
                                <div className="flex justify-between items-start mb-2">
                                    <h3 className="text-xl font-bold text-gray-900 line-clamp-1">{hotel.otelAdi}</h3>
                                    <div className="flex items-center bg-yellow-100 px-2 py-1 rounded">
                                        <Star className="w-4 h-4 text-yellow-500 mr-1 fill-current" />
                                        <span className="text-sm font-bold text-yellow-700">
                                            {hotel.ortalamaPuan ? Number(hotel.ortalamaPuan).toFixed(1) : "0.0"}
                                        </span>
                                    </div>
                                </div>
                                <div className="flex items-center text-gray-600 mb-4">
                                    <MapPin className="w-4 h-4 mr-1" />
                                    <span className="text-sm">{hotel.sehir || "Konum belirtilmemiş"}</span>
                                </div>
                                <p className="text-gray-600 text-sm mb-4 line-clamp-3">
                                </p>

                                <button
                                    onClick={() => window.location.href = `/otel/${hotel.otelId}`}
                                    className="w-full bg-blue-600 text-white py-2 rounded-lg hover:bg-blue-700 transition"
                                >
                                    Detayları Gör
                                </button>
                            </div>
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
};

export default HomePage;
