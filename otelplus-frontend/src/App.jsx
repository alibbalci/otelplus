import { BrowserRouter as Router, Routes, Route, Outlet } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';
import Navbar from './components/Navbar';
import HomePage from './pages/HomePage';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import HotelDetailPage from './pages/HotelDetailPage';
import AdminRoute from './components/AdminRoute';
import AdminLayout from './layouts/AdminLayout';
import AdminDashboard from './pages/admin/AdminDashboard';
import AdminHotelList from './pages/admin/AdminHotelList';
import AdminHotelForm from './pages/admin/AdminHotelForm';
import AdminRoomList from './pages/admin/AdminRoomList';

const PublicLayout = () => (
    <>
        <Navbar />
        <div className="container mx-auto px-4 py-8">
            <Outlet />
        </div>
    </>
);

function App() {
    return (
        <Router>
            <AuthProvider>
                <div className="min-h-screen bg-gray-50 font-sans">
                    <Routes>
                        {/* Public Routes */}
                        <Route element={<PublicLayout />}>
                            <Route path="/" element={<HomePage />} />
                            <Route path="/login" element={<LoginPage />} />
                            <Route path="/register" element={<RegisterPage />} />
                            <Route path="/otel/:id" element={<HotelDetailPage />} />
                        </Route>

                        {/* Admin Routes */}
                        <Route path="/admin" element={<AdminRoute />}>
                            <Route element={<AdminLayout />}>
                                <Route index element={<AdminDashboard />} />
                                <Route path="oteller" element={<AdminHotelList />} />
                                <Route path="oteller/yeni" element={<AdminHotelForm />} />
                                <Route path="oteller/duzenle/:id" element={<AdminHotelForm />} />
                                <Route path="oteller/:otelId/odalar" element={<AdminRoomList />} />
                            </Route>
                        </Route>
                    </Routes>
                </div>
            </AuthProvider>
        </Router>
    );
}

export default App;
