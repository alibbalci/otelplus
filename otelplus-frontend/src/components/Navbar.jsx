import { useContext } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { AuthContext } from '../context/AuthContext';
import { LogOut, Hotel, User } from 'lucide-react';

const Navbar = () => {
    const { currentUser, logout } = useContext(AuthContext);
    const navigate = useNavigate();

    const handleLogout = () => {
        logout();
        navigate('/login');
    };

    return (
        <nav className="bg-white shadow-md">
            <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
                <div className="flex justify-between h-16">
                    <div className="flex">
                        <Link to="/" className="flex-shrink-0 flex items-center">
                            <Hotel className="h-8 w-8 text-blue-600" />
                            <span className="ml-2 text-xl font-bold text-gray-800">OtelPlus</span>
                        </Link>
                    </div>
                    <div className="flex items-center space-x-4">
                        {currentUser ? (
                            <>
                                {currentUser && (currentUser.rol === 'ADMIN' || currentUser.rol === 'ROLE_ADMIN' || currentUser.rol?.toUpperCase() === 'ADMIN') && (
                                    <Link
                                        to="/admin"
                                        className="bg-slate-800 text-white px-3 py-2 rounded-md text-sm font-medium hover:bg-slate-700 transition flex items-center"
                                    >
                                        <div className="w-2 h-2 bg-red-500 rounded-full mr-2"></div>
                                        Admin Panel
                                    </Link>
                                )}
                                <div className="flex items-center text-gray-700">
                                    <User className="h-5 w-5 mr-1" />
                                    <span className="text-sm font-medium">Hesabım</span>
                                </div>
                                <button
                                    onClick={handleLogout}
                                    className="flex items-center px-3 py-2 border border-transparent text-sm leading-4 font-medium rounded-md text-white bg-red-600 hover:bg-red-700 focus:outline-none transition"
                                >
                                    <LogOut className="h-4 w-4 mr-1" />
                                    Çıkış
                                </button>
                            </>
                        ) : (
                            <>
                                <Link
                                    to="/login"
                                    className="text-gray-700 hover:text-blue-600 px-3 py-2 rounded-md text-sm font-medium transition"
                                >
                                    Giriş Yap
                                </Link>
                                <Link
                                    to="/register"
                                    className="bg-blue-600 text-white px-4 py-2 rounded-md text-sm font-medium hover:bg-blue-700 transition"
                                >
                                    Kayıt Ol
                                </Link>
                            </>
                        )}
                    </div>
                </div>
            </div>
        </nav>
    );
};

export default Navbar;
