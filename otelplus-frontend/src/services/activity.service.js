import api from './api';

const ActivityService = {
    getEventsByCityId: async (sehirId) => {
        return await api.get(`/etkinlikler/sehir/${sehirId}`);
    }
};

export default ActivityService;
