/** Definiuje kolekcję ścieżek (kontekstów URL), które mogą prowadzić do widoków aplikacji
 */
export const Pathnames = {
    anonymous: {
    },
    user: {
        homePage: '/user',
        listVMachines: '/user/vmachine/list',
        userProfile: '/user/profile'
    },
    admin: {
        homePage: '/admin',
        listUsers: '/admin/listUsers',
        userProfile: '/admin/profile'
    },

    moderator: {
        homePage: '/moderator',
        userProfile: '/moderator/profile',
        createVMachine: '/moderator/vmachine/create',
        listVMachines: '/moderator/vmachine/list',
    },

    default: {
        homePage: '/',
        login: '/login',
        createUser: '/register'
    }
}