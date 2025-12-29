import { Navigate, Outlet } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

const AdminRoute = () => {
    const { currentUser } = useAuth();

    // Check for "ADMIN" or "ROLE_ADMIN" case-insensitively
    const role = currentUser?.rol?.toUpperCase();
    const isAdmin = role === 'ADMIN' || role === 'ROLE_ADMIN';

    if (!isAdmin) {
        // Redirect to home if not admin
        return <Navigate to="/" replace />;
    }

    return <Outlet />;
};

export default AdminRoute;
