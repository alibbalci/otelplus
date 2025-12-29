import api from './api';

const ReportService = {
    getGeneralReport: async (startDate, endDate) => {
        return await api.get(`/admin/raporlar/genel?baslangic=${startDate}&bitis=${endDate}`);
    },

    getCityReport: async (startDate, endDate) => {
        return await api.get(`/admin/raporlar/sehir?baslangic=${startDate}&bitis=${endDate}`);
    }
};

export default ReportService;
