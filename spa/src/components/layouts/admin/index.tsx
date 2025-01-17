import { ReactNode } from 'react'
import { Pathnames } from '../../../router/pathnames'
import { useNavigate } from 'react-router-dom'
import { FadingAlertComponent } from "../../alert/FadingAlert";
import { useUserSession } from '../../../model/UserContext';

interface LayoutProps {
    children: ReactNode
}

export const AdminLayout = ({ children }: LayoutProps) => {
    // Klient ma dostęp do home, swojego profilu, listy maszyn, swoich wypożyczeń
    const navigate = useNavigate()
    const { clearUser } = useUserSession();

    return (
        <div className="bg-dark text-light"> {/* Ciemne tło i jasny tekst */}
            {/* Pasek nawigacji */}
            <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
                <div className="container-fluid">
                    <a
                        className="navbar-brand"
                        onClick={() => navigate(Pathnames.admin.homePage)}
                        style={{ cursor: 'pointer' }}
                    >
                        Admin Panel
                    </a>
                    <button
                        className="navbar-toggler"
                        type="button"
                        data-bs-toggle="collapse"
                        data-bs-target="#navbarNav"
                        aria-controls="navbarNav"
                        aria-expanded="false"
                        aria-label="Toggle navigation"
                    >
                        <span className="navbar-toggler-icon"></span>
                    </button>
                    <div className="collapse navbar-collapse" id="navbarNav">
                        <ul className="navbar-nav ms-auto">
                            <li className="nav-item">
                                <button
                                    className="btn btn-outline-light mx-2"
                                    onClick={() => navigate(Pathnames.admin.homePage)}
                                >
                                    Home
                                </button>
                            </li>
                            <li className="nav-item">
                                <button
                                    className="btn btn-outline-light mx-2"
                                    onClick={() => navigate(Pathnames.admin.listUsers)}
                                >
                                    List Users
                                </button>
                            </li>
                            <li className="nav-item">
                                <button
                                    className="btn btn-outline-light mx-2"
                                    onClick={() => navigate(Pathnames.admin.userProfile)}
                                >
                                    Profile
                                </button>
                            </li>
                            <li className="nav-item">
                                <button
                                    className="btn btn-outline-light mx-2"
                                    onClick={() => navigate(Pathnames.admin.createUser)}
                                >
                                    Create User
                                </button>
                            </li>
                            <li className="nav-item">
                                <button
                                    className="btn btn-outline-light mx-2"
                                    onClick={() => {
                                        clearUser();
                                        navigate(Pathnames.default.homePage);
                                    }}
                                >
                                    Log Out
                                </button>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>

            {/* Alert */}
            <div style={{ position: "fixed", overflow: "visible", width: "inherit" }}>
                <FadingAlertComponent />
            </div>

            {/* Zawartość strony */}
            <div className="container mt-4">
                {children}
            </div>
        </div>
    )
}
