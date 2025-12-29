import api from './api';

const ReservationService = {
    createReservation: async (reservationData) => {
        return await api.post('/rezervasyonlar/rezervasyon', reservationData);
    },

    getUserReservations: async (userId) => {
        return await api.get(`/rezervasyonlar/kullanici/${userId}`);
    }
};

export default ReservationService;
