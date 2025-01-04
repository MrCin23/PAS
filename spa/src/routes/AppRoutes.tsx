import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import HomePage from "../pages/HomePage.tsx";
import ErrorPage from "../pages/ErrorPage.tsx";
import CreateUser from "../pages/CreateUser.tsx";
import ListUsers from "../pages/ListUsers.tsx";
import LogInUser from "../pages/LogInUser.tsx";

const AppRoutes = () => (
    <Router>
        <Routes>
            <Route path="/" element={<HomePage />} />
            <Route path="/register" element={<CreateUser />} />
            <Route path="/error" element={<ErrorPage />} />
            <Route path="/listUsers" element={<ListUsers />} />
            <Route path="/login" element={<LogInUser />} />
        </Routes>
    </Router>
);

export default AppRoutes;
