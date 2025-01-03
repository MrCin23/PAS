import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import HomePage from "../pages/HomePage.tsx";
import ErrorPage from "../pages/ErrorPage.tsx";
import CreateUser from "../pages/CreateUser.tsx";

const AppRoutes = () => (
    <Router>
        <Routes>
            <Route path="/" element={<HomePage />} />
            <Route path="/register" element={<CreateUser />} />
            <Route path="/error" element={<ErrorPage />} />
        </Routes>
    </Router>
);

export default AppRoutes;
