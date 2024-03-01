import React, {StrictMode, useState} from "react";
const ReactDOM= require('react-dom');
import {BrowserRouter, Route, Routes, useNavigate} from "react-router-dom";
import {HomePage} from "./presenter/homePage";
import {Login} from "./presenter/login";
import {TopBar} from "./presenter/topBar";
import {MainContentView} from "./view/mainContentView";
import {FooterView} from "./view/footerView";
import {ContainerView} from "./view/ContainerView";
import {logout} from "./presenter/api/apiCallHandler";

/**
 * Root component for the application
 * @returns {JSX.Element} the rendered application
 * @constructor
 */
function App() {
    const navigate = useNavigate()
    const [user, setUser] = useState(null)
    const [isLoading, setIsLoading] = useState(true)
    const [pathRequestedByUnauthorizedUser, setPathRequestedByUnauthorizedUser] = useState('')

    function onLoggedIn(user){
        setUser(user)
        navigate(pathRequestedByUnauthorizedUser || '/')
        setPathRequestedByUnauthorizedUser('')
    }

    function persistUserToLocalStorage() {
        if(user)
            localStorage.setItem('user', JSON.stringify(user));
    }

    function getUserFromLocalStorage() {
        const userFromStorage = JSON.parse(localStorage.getItem('user'));
        if(userFromStorage && userFromStorage !== user)
            setUser(userFromStorage);
        setIsLoading(false)
    }

    React.useEffect(() => {
        persistUserToLocalStorage()
        if(window.location.pathname !== '/login' && !user) {
            setPathRequestedByUnauthorizedUser(window.location.pathname)
            navigate('/login')
        }
        else if(window.location.pathname === '/login' && user)
            navigate('/')
    }, [user]);

    React.useEffect(() => { // Runs when component is created
        getUserFromLocalStorage()
    }, [])

    function onLogout() {
        logout().then(() => {
            localStorage.clear()
            navigate('/login')
        });
    }

    return (
        <div>
            <ContainerView>
                <TopBar user={user}/>
                <button onClick={onLogout}>Logout</button>
                <MainContentView>
                    {!isLoading &&
                        <Routes>
                            <Route path='/' element={<HomePage user={user}/>}/>
                            <Route path='/login' element={<Login onLoggedIn={onLoggedIn}/>}/>
                        </Routes>
                    }
                </MainContentView>
                <FooterView/>
            </ContainerView>
        </div>
    )
}

ReactDOM.render(
    <StrictMode>
        <BrowserRouter>
            <App />
        </BrowserRouter>
    </StrictMode>,
    document.getElementById('react')
)