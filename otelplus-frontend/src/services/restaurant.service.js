import api from './api';

const RestaurantService = {
    getRestaurantsByHotelId: async (otelId) => {
        return await api.get(`/restoranlar/otel/${otelId}`);
    },

    getMenuByRestaurantId: async (restoranId) => {
        return await api.get(`/menuler/restoran/${restoranId}`);
    }
};

export default RestaurantService;
