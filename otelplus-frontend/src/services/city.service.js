import api from './api';

const CityService = {
    getAllCities: async () => {
        return await api.get('/sehirler');
    }
};

export default CityService;
