/** Definiuje kolekcję ścieżek (kontekstów URL), które mogą prowadzić do widoków aplikacji
 */
export const Pathnames = {
    anonymous: {
    },
    user: {
    },
    admin: {
        listUsers: '/listUsers',
        createUser: '/register',
        login: '/login',
    },

    default: {
        homePage: '/',
    }
}