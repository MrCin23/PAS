import { Pathnames } from './pathnames'

import {HomePage} from "../pages/HomePage";
import {ListUsers} from "../pages/ListUsers";
import {CreateUser} from "../pages/CreateUser";
import {LogInUser} from "../pages/LogInUser";
import {ListVMachines} from "../pages/ListVMachines.tsx";
import {UserProfile} from "../pages/Profile.tsx";
import {CreateVMachine} from "../pages/CreateVMachine.tsx";

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
    },
    {
        path: Pathnames.default.login,
        Component: LogInUser,
    },
    {
        path: Pathnames.default.createUser,
        Component: CreateUser,
    }

]

export const adminRoutes: RouteType[] = [
    {
        path: Pathnames.admin.homePage,
        Component: HomePage,
    },{
        path: Pathnames.admin.listUsers,
        Component: ListUsers,
    },
    {
        path: Pathnames.admin.userProfile,
        Component: UserProfile,
    }

]

export const userRoutes: RouteType[] = [
    {
        path: Pathnames.user.homePage,
        Component: HomePage,
    },
    {
        path: Pathnames.user.listVMachines,
        Component: ListVMachines,
    },
    {
        path: Pathnames.user.userProfile,
        Component: UserProfile,
    }
]

export const moderatorRoutes: RouteType[] = [
    {
        path: Pathnames.moderator.homePage,
        Component: HomePage,
    },
    {
        path: Pathnames.moderator.createVMachine,
        Component: CreateVMachine,
    },
    {
        path: Pathnames.moderator.userProfile,
        Component: UserProfile,
    },
    {
        path: Pathnames.moderator.listVMachines,
        Component: ListVMachines,
    }
]

export const anonymousRoutes: RouteType[] = [
]