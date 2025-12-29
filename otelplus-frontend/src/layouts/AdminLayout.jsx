import { Link, Outlet, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { LayoutDashboard, Users, Hotel, LogOut, Home } from 'lucide-react';

const AdminLayout = () => {
    const { logout, currentUser } = useAuth();
    const navigate = useNavigate();

    const handleLogout = () => {
        logout();
        navigate('/login');
    };

    return (
        <div className="flex h-screen bg-gray-100">
            {/* Sidebar */}
            <div className="w-64 bg-slate-800 text-white flex flex-col">
                <div className="p-6 border-b border-slate-700">
                    <h2 className="text-2xl font-bold flex items-center">
                        <span className="text-blue-500 mr-2">Otel</span>Plus
                        <span className="text-xs bg-red-600 text-white px-2 py-0.5 rounded ml-2">Admin</span>
                    </h2>
                </div>

                <nav className="flex-1 p-4 space-y-2">
                    <Link to="/admin" className="flex items-center p-3 text-gray-300 hover:bg-slate-700 hover:text-white rounded-lg transition">
                        <LayoutDashboard className="w-5 h-5 mr-3" />
                        Dashboard
                    </Link>
                    <Link to="/admin/oteller" className="flex items-center p-3 text-gray-300 hover:bg-slate-700 hover:text-white rounded-lg transition">
                        <Hotel className="w-5 h-5 mr-3" />
                        Otelleri Yönet
                    </Link>

                </nav>

                <div className="p-4 border-t border-slate-700">
                    <div className="flex items-center mb-4 px-3">
                        <div className="w-8 h-8 rounded-full bg-slate-600 flex items-center justify-center mr-3">
                            <span className="font-bold">{currentUser?.kullaniciAdi?.charAt(0).toUpperCase()}</span>
                        </div>
                        <div>
                            <p className="text-sm font-medium">{currentUser?.kullaniciAdi}</p>
                            <p className="text-xs text-gray-400">Yönetici</p>
                        </div>
                    </div>
                    <Link to="/" className="flex items-center p-2 text-gray-400 hover:text-white transition mb-2">
                        <Home className="w-4 h-4 mr-2" />
                        Siteye Dön
                    </Link>
                    <button
                        onClick={handleLogout}
                        className="flex items-center w-full p-2 text-red-400 hover:bg-red-900/20 rounded-lg transition"
                    >
                        <LogOut className="w-4 h-4 mr-2" />
                        Çıkış Yap
                    </button>
                </div>
            </div>

            {/* Main Content */}
            <div className="flex-1 overflow-auto">
                <header className="bg-white shadow-sm p-4 flex justify-between items-center">
                    <h1 className="text-xl font-semibold text-gray-800">Yönetim Paneli</h1>
                </header>
                <main className="p-8">
                    <Outlet />
                </main>
            </div>
        </div>
    );
};

export default AdminLayout;
