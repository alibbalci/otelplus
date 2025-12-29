import api from './api';

const PlaceService = {
    getPlacesByCityId: async (sehirId) => {
        return await api.get(`/gezilecekyerler/sehir/${sehirId}`);
    }
};

export default PlaceService;
