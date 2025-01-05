
import { AppBar, Button, Container, Toolbar } from '@mui/material'
import { ReactNode } from 'react'
import { Pathnames } from '../../../router/pathnames'
import { useNavigate } from 'react-router-dom'
import {FadingAlertComponent} from "../../alert/FadingAlert";

interface LayoutProps {
    children: ReactNode
}

export const DefaultLayout = ({ children }: LayoutProps) => {
    // Udostępnia funkcję pozwalającą na zmianę widoku na inny, zgodnie z określoną ścieżką (pathname)
    const navigate = useNavigate()


    return (
        <div>
            <AppBar position="static">
                <Toolbar sx={{ display: 'flex'}}>
                    <Button onClick={() => navigate(Pathnames.default.homePage)} sx={{ my: 2, mx: 2, color: 'white' }}>
                        Go to Home
                    </Button>
                    <Button onClick={() => navigate(Pathnames.admin.listUsers)} sx={{ my: 2, mx: 2, color: 'white' }}>
                        List Persons
                    </Button>
                    <Button onClick={() => navigate(Pathnames.admin.createUser)} sx={{ my: 2, mx: 2, color: 'white' }}>
                        Create Person
                    </Button>
                    <Button onClick={() => navigate(Pathnames.admin.login)} sx={{ my: 2, mx: 2, color: 'white' }}>
                        LogIn User
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
