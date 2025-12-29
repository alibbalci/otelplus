import api from './api';

const AuthService = {
    login: async (username, password) => {
        const response = await api.post('/auth/login', {
            kullaniciAdi: username,
            kullaniciSifre: password,
        });
        if (response.data.token) {
            localStorage.setItem('token', response.data.token);
            // Save the entire user object (minus sensitive info if needed, but DTO is safe)
            localStorage.setItem('user', JSON.stringify(response.data));
        }
        return response.data;
    },

    register: async (userData) => {
        return await api.post('/auth/register', userData);
    },

    logout: () => {
        localStorage.removeItem('token');
        localStorage.removeItem('user');
    },

    getCurrentUser: () => {
        const userStr = localStorage.getItem('user');
        if (userStr) {
            return JSON.parse(userStr);
        }
        return null;
    }
};

export default AuthService;
