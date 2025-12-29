import api from './api';

const HotelService = {
    getAllHotels: async () => {
        return await api.get('/oteller');
    },

    getHotelById: async (id) => {
        return await api.get(`/oteller/${id}`);
    },

    searchHotels: async (sehir, sort = 'puan_desc') => {
        return await api.get(`/oteller/search-fn?sehir=${encodeURIComponent(sehir)}&sort=${sort}`);
    },

    addHotel: async (hotelData) => {
        return await api.post('/admin/oteller', hotelData);
    },

    updateHotel: async (id, hotelData) => {
        return await api.put(`/admin/oteller/${id}`, hotelData);
    },

    deleteHotel: async (id) => {
        return await api.delete(`/admin/oteller/${id}`);
    },

    // ---------------- ODA YÖNETİMİ ----------------
    getRooms: async (otelId) => {
        return await api.get(`/admin/oteller/${otelId}/odalar`);
    },

    addRoom: async (otelId, roomData) => {
        return await api.post(`/admin/oteller/${otelId}/odalar`, roomData);
    },

    deleteRoom: async (odaId) => {
        return await api.delete(`/admin/odalar/${odaId}`);
    }
};

export default HotelService;
