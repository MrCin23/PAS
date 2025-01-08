import { AppBar, Button, Container, Toolbar } from '@mui/material'
import { ReactNode } from 'react'
import { Pathnames } from '../../../router/pathnames'
import { useNavigate } from 'react-router-dom'
import {FadingAlertComponent} from "../../alert/FadingAlert";
import { useUserSession } from '../../../model/UserContext';

interface LayoutProps {
    children: ReactNode
}

export const ClientLayout = ({ children }: LayoutProps) => {
    // Klient ma dostęp do home, swojego profilu, listy maszyn, swoich wypożyczeń
    const navigate = useNavigate()
    const { clearUser } = useUserSession();

    return (
        <div>
            <AppBar position="static">
                <Toolbar sx={{ display: 'flex'}}>
                    <Button onClick={() => navigate(Pathnames.user.homePage)} sx={{ my: 2, mx: 2, color: 'white' }}>
                        Home
                    </Button>
                    <Button onClick={() => navigate(Pathnames.user.listVMachines)} sx={{ my: 2, mx: 2, color: 'white' }}>
                        List Virtual Machines
                    </Button>
                    <Button onClick={() => navigate(Pathnames.user.myRents)} sx={{ my: 2, mx: 2, color: 'white' }}>
                        Rents
                    </Button>
                    <Button onClick={() => navigate(Pathnames.user.userProfile)} sx={{ my: 2, mx: 2, color: 'white' }}>
                        Profile
                    </Button>
                    <Button onClick={() => {
                        clearUser();
                        navigate(Pathnames.default.homePage)}} sx={{ my: 2, mx: 2, color: 'white' }}>
                        Log out
                    </Button>
                </Toolbar>
            </AppBar>
            <div style={{ position: "fixed", overflow: "visible", width: "inherit" }}>
                <FadingAlertComponent />
                {/*<p>error</p>*/}
                {/*TODO to correct*/}
            </div>
            <Container sx={{ p: 2 }}>
                {children}
            </Container>
        </div>
    )
}
