/** Definiuje kolekcję ścieżek (kontekstów URL), które mogą prowadzić do widoków aplikacji
 */
export const Pathnames = {
    anonymous: {
    },
    user: {
        listVMachines: '/vmachine/list',
        userProfile: '/user/profile',
    },
    admin: {
        listUsers: '/listUsers',
        createUser: '/register',
        login: '/login',
    },
    //
    // moderator: {
    //   createVMachine: '/vmachine/create',
    // },

    default: {
        homePage: '/',
    }
}