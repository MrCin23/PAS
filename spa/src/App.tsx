import './App.css'

import {BrowserRouter as Router} from 'react-router-dom'
import {RoutesComponent} from "./router";
import {FadingAlertContextProvider} from "./contexts/FadingAlert/FadingAlertContext.tsx";
import {UserProvider} from "./model/UserContext.tsx";

export const App = () => (
    // Dostarcza komponentom aplikacji kontekst, który umożliwia pokazywanie okna alertu
    //  podgląd wypożyczeń przez admina/moderatora (klikasz w Usera i widzisz jego wypożyczenia)
    // potwierdzanie wykonania ważnych operacji <- endRent, deleteVM, rentVM
    // daty z bazy
    <UserProvider>
        {/*Test czy to działa*/}
        <FadingAlertContextProvider>
            {/* Włącza mechanizm rutera - nawigacji po ścieżkach aplikacji */}
            <Router>
                <RoutesComponent/>
            </Router>
        </FadingAlertContextProvider>
    </UserProvider>
)

export default App
