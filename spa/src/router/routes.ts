import { Pathnames } from './pathnames'

import {HomePage} from "../pages/HomePage";
import {ListUsers} from "../pages/ListUsers";
import {CreateUser} from "../pages/CreateUser";
import {LogInUser} from "../pages/LogInUser";
import {ListVMachines} from "../pages/ListVMachines.tsx";
import {UserProfile} from "../pages/Profile.tsx";

/** Definiuje pseudo-mapy - tablice par ścieżka (kontekst URL) - komponent
 * Takie mapy są wykorzystywane przez mechanizm rutera, aby zdefiniować nawigację między widokami
 * @see Pathnames
 */
export type RouteType = {
    Component: () => React.ReactElement,
    path: string
}

export const defaultRoutes: RouteType[] = [
    {
        path: Pathnames.default.homePage,
        Component: HomePage,
    }

]

export const adminRoutes: RouteType[] = [
    {
        path: Pathnames.admin.listUsers,
        Component: ListUsers,
    },
    {
        path: Pathnames.admin.createUser,
        Component: CreateUser,
    },
    {
        path: Pathnames.admin.login,
        Component: LogInUser,
    }

]

export const userRoutes: RouteType[] = [
    {
        path: Pathnames.user.listVMachines,
        Component: ListVMachines,
    },
    {
        path: Pathnames.user.userProfile,
        Component: UserProfile,
    }
]

export const anonymousRoutes: RouteType[] = [
]