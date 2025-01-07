import {Route, Routes} from 'react-router-dom'
import {defaultRoutes, adminRoutes, userRoutes} from './routes.ts'
import {DefaultLayout} from "../components/layouts/default";

/** Komponent rutera definiuje możliwe ścieżki (konteksty URL), które prowadzą do określonych widoków (komponentów)
 * Używana jest do tego mapa łącząca ścieżkę z komponentem.
 * Tu użyta jest konstrukcja używająca wielu map, w zamyśle dzieli ona widoki ze względu na dostępność dla poszczególnych poziomów dostępu
 * Dla uproszczenia we wszystkich przypadkach jest używany ten sam szablon strony, ale można by stworzyć wiele szablonów i zmieniać wygląd aplikacji
 *
 * @see routes
 * @see DefaultLayout
 */
export const RoutesComponent = () => {


    return (
        <Routes>
            {defaultRoutes.map(({path, Component}) => (
                <Route key={path} path={path} element={
                    <DefaultLayout>
                        <Component />
                    </DefaultLayout>
                }
                />
            ))}
            {adminRoutes.map(({path, Component}) => (
                <Route key={path} path={path} element={
                    <DefaultLayout>
                        <Component />
                    </DefaultLayout>
                }
                />
            ))}
            {userRoutes.map(({path, Component}) => (
                <Route key={path} path={path} element={
                    <DefaultLayout>
                        <Component />
                    </DefaultLayout>
                }
                />
            ))}
        </Routes>
    )
}