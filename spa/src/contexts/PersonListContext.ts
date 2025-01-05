import {createContext} from "react";
import {PersonsApiMock} from "../api/PersonsApiMock.ts";

// Typowo do ustawienia wartości kontekstu używany jest <Provider>.
// Ale w przypadku tego kontekstu istnieje tylko jedna wartość, którą jest instancja PersonsApiMock.
// Więc możemy po prostu użyć jej jako domyślnej.
export const PersonListContext = createContext(new PersonsApiMock())
